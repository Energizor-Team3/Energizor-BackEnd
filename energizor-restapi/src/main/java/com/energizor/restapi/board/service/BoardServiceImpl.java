package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.entity.*;
import com.energizor.restapi.board.repository.*;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.dto.UserDTO;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sun.tools.jconsole.JConsoleContext;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardUserRepository userRepository;
    private final InterestBoardRepository interestBoardRepository;
    private final TemporaryBoardRepository temporaryBoardRepository;
    private final FileRepository fileRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final ModelMapper modelMapper;

    private final int PREV_OR_NEXT=0;
    private final int PREV_ARTICLE=0;
    private final int NEXT_ARTICLE=1;

    @Value("${board.upload.path}")
    private String uploadPath;


    @Override
    public PageResultDTO<BoardDTO,Board> findAllList(PageRequestDTO pageRequestDTO) {
        log.info("[BoardService] findAllList start=================");

        log.info("pageRequestDTO : "+pageRequestDTO);

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("boardCode").descending());

        BooleanBuilder booleanBuilder = getSearchBoard(pageRequestDTO); // 검색 조건 처리

        Page<Board> result = boardRepository.findAll(booleanBuilder,pageable); // Querydsl 사용


        /* Entity -> DTO로 변환 */
        Function<Board,BoardDTO> fn=(board-> {
            BoardDTO boardDTO=modelMapper.map(board,BoardDTO.class);

            // user의 team정보가 있을 경우에만 teamName 설정
            if(board.getUser()!=null && board.getUser().getTeam()!=null) {
                boardDTO.setTeamName(board.getUser().getTeam().getTeamName());
                boardDTO.setDeptName(board.getUser().getTeam().getDept().getDeptName());
            }
            return boardDTO;
        });

        log.info("[BoardService] findAllList End======================");

        PageResultDTO<BoardDTO,Board> pageResultDTO=new PageResultDTO<>(result,fn);


        return pageResultDTO;
    }



    @Override
    public BoardDTO findDetailBoard(int boardCode) {

        log.info("[BoardService] findDetailBoard start ====================");

        Board board= boardRepository.findByCode(boardCode);
        BoardDTO boardDTO=modelMapper.map(board,BoardDTO.class);

        if(board.getUser()!=null && board.getUser().getTeam()!=null) {
            boardDTO.setTeamName(board.getUser().getTeam().getTeamName());
        }
        if(board.getUser()!=null && board.getUser().getTeam()!=null) {
            boardDTO.setDeptName(board.getUser().getTeam().getDept().getDeptName());
        }

        log.info("[BoardService] findDetailBoard ======================");

        return boardDTO;
    }



    @Override
    public BoardDTO register(BoardDTO boardDTO, UserDTO principal, MultipartFile[] uploadFiles) {
        log.info("[BoardService] register start===============");
        log.info("principal :!!!!!!!!!!!! " + principal);
        int boardCode = boardDTO.getBoardCode();

        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        Board board = boardRepository.findByCode(boardCode);
        int boardTypeCode=boardDTO.getBoardTypeCode();

        BoardType boardType= boardTypeRepository.findByCode(boardTypeCode);

        log.info("isEmpty : " + uploadFiles.length);


            boardTypeRepository.save(boardType);
            log.info("==============boardType : "+boardType);
            int userCode = principal.getUserCode();
            User user = userRepository.findByCode(userCode);

            Board insertBoard = modelMapper.map(boardDTO, Board.class);

            if (Boolean.TRUE.equals(boardDTO.isTemporaryOpt())) {
                // 임시저장
                TemporaryBoard temporaryBoard = new TemporaryBoard();
                temporaryBoard.setTitle(boardDTO.getTitle());
                temporaryBoard.setContent(boardDTO.getContent());
                temporaryBoard.setUser(user);
                temporaryBoard.setViewCount(boardDTO.getViewCount());
                temporaryBoard.setBoardType(insertBoard.getBoardType());
                TemporaryBoard savedTemporaryBoard = temporaryBoardRepository.save(temporaryBoard);

                log.info("임시저장 성공");

                BoardDTO tempBoardDto = new BoardDTO();
                tempBoardDto.setBoardCode(savedTemporaryBoard.getTemporaryCode());
                return tempBoardDto;

            } else {
                insertBoard.user(user);
                insertBoard.boardType(boardType);
                Board savedBoard = boardRepository.save(insertBoard);

                for (MultipartFile uploadFile : uploadFiles) {
                    BoardFile boardFile = new BoardFile();

                    // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
                    String originalName = uploadFile.getOriginalFilename();
                    log.info("originalName : " + originalName);
                    String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

                    log.info("fileName : " + fileName);

                    // 날짜 폴더 생성
                    String folderPath = makeFolder();
                    log.info("folderPath : " + folderPath);

                    // UUID
                    String uuid = UUID.randomUUID().toString();
                    log.info("uuid : " + uuid);

                    // 저장할 파일 이름 중간에 "_"를 이용해서 구분
                    String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
                    // File.separator > unix / or window \\ 구분자 추가됨
                    log.info("saveName : " + saveName);

                    Path savePath = Paths.get(saveName);
                    log.info("savePath : " + savePath);

                    try {
                        uploadFile.transferTo(savePath); // 실제 이미지 저장

                        if (uploadFile.getContentType() != null && uploadFile.getContentType().startsWith("image")) {
                            // 섬네일 생성
                            String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                            // 섬네일 파일 이름은 중간에 s_로 시작하도록
                            File thumbnailFile = new File(thumbnailSaveName);
                            // 섬네일 생성
                            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                        }

                        resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    boardFile.setFileName(fileName);
                    boardFile.setUuid(uuid);
                    boardFile.setFolderPath(folderPath);
                    boardFile.setFileType(uploadFile.getContentType());
                    boardFile.setFileSize((int) uploadFile.getSize());
                    boardFile.setBoard(savedBoard);

                    fileRepository.save(boardFile);
                BoardDTO savedBoardDTO = modelMapper.map(savedBoard, BoardDTO.class);
                savedBoardDTO.setUserCode(user.getUserCode());
                log.info("[BoardService] register end ==================");
                return savedBoardDTO;

            }

        }
        return null;
    }

    @Transactional
    @Override
    public String update(BoardDTO boardDTO,UserDTO principal) {

        log.info("[BoardService] update start =================");
        log.info("principal : "+principal);

        Board board=boardRepository.findByCode(boardDTO.getBoardCode());
        if(board==null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        if(board.getUser().getUserCode()!=principal.getUserCode()) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        board.title(boardDTO.getTitle())
                .content(board.getContent())
                .build();

        return "게시글 수정 성공";
    }

    @Transactional
    @Override
    public BoardDTO delete(int boardCode,UserDTO principal) {
        log.info("[BoardService] delete start =================");

        Optional<Board> boardResult= Optional.ofNullable(boardRepository.findByCode(boardCode));

        if(!boardResult.isPresent()) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        Board board=boardResult.get();

        if (board.getUser().getUserCode()!=principal.getUserCode()) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        LocalDateTime dateTime=LocalDateTime.now();


        Optional<List<BoardComment>> commentResult=boardCommentRepository.findByBoardCode(board.getBoardCode());

            if(commentResult.isPresent()) {
                List<BoardComment> commentEntity=commentResult.get();

                commentEntity.forEach(el-> {
                    el.changeReplyDeleteDate(dateTime);
                    boardCommentRepository.save(el);
                });
            }
        board.changeBoardDeletedAt(dateTime);
        Board boardEntity=boardRepository.save(board);

        BoardDTO response=modelMapper.map(boardEntity, BoardDTO.class);

        return response;

    }


    @Override
    public List<BoardCommentDTO> findComment(int boardCode) {

        log.info("[BoardService] findComment start ======================");
        log.info("boardCode : "+boardCode);
        List<BoardComment> boardComments= boardCommentRepository.findByCodeWithComment(boardCode);

        List<BoardCommentDTO> boardCommentDTOS=boardComments.stream()
                .map(boardComment -> new BoardCommentDTO(
                        boardComment.getCommentCode(),
                        boardComment.getCommentContent(),
                        boardComment.getRegisterDate(),
                        boardComment.getUser().getTeam().getDept().getDeptName(),
                        boardComment.getUser().getTeam().getTeamName(),
                        boardComment.getUser().getUserName(),
                        boardComment.getUser().getUserRank(),
                        boardComment.getBoard().getBoardCode())).collect(Collectors.toList());

        log.info("=========================boardComentDTO : "+boardCommentDTOS);


        return boardCommentDTOS;

    }

    @Override
    public BoardCommentDTO registerComment(BoardCommentDTO boardCommentDTO,UserDTO principal) {
        log.info("[BoardService] registerComment start===============");

        Board board=boardRepository.findByCode(boardCommentDTO.getBoardCode());
        User user=userRepository.findByCode(principal.getUserCode());

        BoardComment comment=BoardComment.builder()
                .commentCode(boardCommentDTO.getCommentCode())
                .commentContent(boardCommentDTO.getCommentContent())
                .board(board)
                .user(user)
                .build();

        BoardComment boardComment=boardCommentRepository.save(comment);
        BoardCommentDTO commentDTO=modelMapper.map(boardComment,BoardCommentDTO.class);

        return commentDTO;

    }

    @Transactional
    @Override
    public BoardCommentDTO updateComment(int commentCode,UserDTO principal) {

        log.info("[BoardService] updateComment start ================ ");

        BoardComment boardComment= boardCommentRepository.findByCommentCode(commentCode);
        BoardCommentDTO boardCommentDTO=modelMapper.map(boardComment, BoardCommentDTO.class);

            if(boardComment.getUser().getUserCode()!= principal.getUserCode()) {
                throw new SecurityException("수정 권한이 없습니다.");
            }

            boardComment.commentContent(boardCommentDTO.getCommentContent());
            BoardComment updateComment=boardCommentRepository.save(boardComment);

            BoardCommentDTO commentDTO=modelMapper.map(updateComment,BoardCommentDTO.class);

            return commentDTO;

    }

    @Override
    public BoardCommentDTO deleteComment(int commentCode,UserDTO principal) {

        log.info("[BoardService] deleteComment start =================");
        BoardComment boardComment= boardCommentRepository.findByCommentCode(commentCode);
        LocalDateTime dateTime=LocalDateTime.now();

        if(boardComment.getUser().getUserCode()!= principal.getUserCode()) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        boardComment.changeReplyDeleteDate(dateTime);
        BoardComment updateBoardComment=boardCommentRepository.save(boardComment);


        BoardCommentDTO boardCommentDTO=modelMapper.map(updateBoardComment,BoardCommentDTO.class);

        return boardCommentDTO;
    }

    @Override
    public InterestBoardDTO registerInterestBoard(int boardCode, int ownerCode) {
        User owner=userRepository.findByCode(ownerCode);
        Board board=boardRepository.findByCode(boardCode);
        User user=userRepository.findByCode(board.getUser().getUserCode());

        log.info("owner : "+owner);
        log.info("board : "+board);
        InterestBoard interestBoard=new InterestBoard();
        interestBoard.owner(owner);
        interestBoard.board(board);
        interestBoard.user(user);


        InterestBoard interestEntity=interestBoardRepository.save(interestBoard);
        InterestBoardDTO interestDTO=modelMapper.map(interestEntity,InterestBoardDTO.class);

        interestDTO.setDeptName(board.getUser().getTeam().getDept().getDeptName());
        interestDTO.setTeamName(board.getUser().getTeam().getTeamName());
        interestDTO.setTitle(board.getTitle());
        interestDTO.setContent(board.getContent());
        interestDTO.setViewCount(board.getViewCount());
        return interestDTO;

    }

    @Transactional
    @Override
    public boolean deleteInterestBoard(int interestCode,int ownerCode) {

        InterestBoard interestBoard=interestBoardRepository.findByInterestCode(interestCode);

        if(interestBoard.getOwner().getUserCode()!=ownerCode) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        interestBoardRepository.deleteById(interestCode);

        return true;
    }




    @Override
    public PageResultDTO<InterestBoardDTO,InterestBoard> findInterestBoardList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO : "+pageRequestDTO);

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("interestCode").descending());

        BooleanBuilder booleanBuilder = getSearchInterestBoard(pageRequestDTO); // 검색 조건 처리

        Page<InterestBoard> result = interestBoardRepository.findAll(booleanBuilder, pageable); // Querydsl 사용

        Function<InterestBoard,InterestBoardDTO> fn=(entity->interestEntityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    private BooleanBuilder getSearchBoard(PageRequestDTO requestDTO) {
        // Querydsl 처리
        int boardTypeCode=requestDTO.getBoardTypeCode();

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QBoard qBoard = QBoard.board;

        if(boardTypeCode>0) {
            booleanBuilder.and(qBoard.boardType.boardTypeCode.eq(boardTypeCode));
        }

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qBoard.boardCode.gt(0);
        // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) { // 제목
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if (type.contains("c")) { // 내용
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if (type.contains("w")) { // 작성자
            conditionBuilder.or(qBoard.user.userName.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    private BooleanBuilder getSearchInterestBoard(PageRequestDTO requestDTO) {
        // Querydsl 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QInterestBoard qInterestBoard = QInterestBoard.interestBoard;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qInterestBoard.interestCode.gt(0);
        // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) { // 제목
            conditionBuilder.or(qInterestBoard.board.title.contains(keyword));
        }
        if (type.contains("c")) { // 내용
            conditionBuilder.or(qInterestBoard.board.content.contains(keyword));
        }
        if (type.contains("w")) { // 작성자
            conditionBuilder.or(qInterestBoard.user.userName.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    private BooleanBuilder getSearchTemporaryBoard(PageRequestDTO requestDTO) {
        // Querydsl 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QTemporaryBoard qTemporaryBoard = QTemporaryBoard.temporaryBoard;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qTemporaryBoard.temporaryCode.gt(0);
        // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) { // 제목
            conditionBuilder.or(qTemporaryBoard.title.contains(keyword));
        }
        if (type.contains("c")) { // 내용
            conditionBuilder.or(qTemporaryBoard.content.contains(keyword));
        }
        if (type.contains("w")) { // 작성자
            conditionBuilder.or(qTemporaryBoard.user.userName.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    @Override
    public InterestBoardDTO findDetailInterestBoard(int interestCode) {

        Object result=interestBoardRepository.findDetailByInterestCode(interestCode);

        Object[] arr = (Object[]) result;

        String res = result.toString();



        System.out.println(res);
        log.info(res);


        return interestEntityToDto2((InterestBoard) arr[0], (Board) arr[1], (User) arr[2]);

    }

    @Override
    public PageResultDTO findTemporaryBoardList(PageRequestDTO pageRequestDTO) {

        log.info("pageRequestDTO : "+pageRequestDTO);

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("temporaryCode").descending());

        BooleanBuilder booleanBuilder = getSearchTemporaryBoard(pageRequestDTO); // 검색 조건 처리

        Page<TemporaryBoard> result = temporaryBoardRepository.findAll(booleanBuilder, pageable); // Querydsl 사용

        Function<TemporaryBoard,TemporaryBoardDTO> fn=(entity->temporaryEntityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public TemporaryBoardDTO findDetailTemporaryBoard(int temporaryCode) {
        TemporaryBoard temporaryBoard=temporaryBoardRepository.findByTemporaryCode(temporaryCode);
        TemporaryBoardDTO temporaryBoardDTO=modelMapper.map(temporaryBoard,TemporaryBoardDTO.class);

        temporaryBoardDTO.setDeptName(temporaryBoard.getUser().getTeam().getDept().getDeptName());
        temporaryBoardDTO.setTeamName(temporaryBoard.getUser().getTeam().getTeamName());


        return temporaryBoardDTO;
    }

    @Transactional
    @Override
    public boolean deleteTemporaryBoard(int temporaryCode,UserDTO principal) {

        int userCode=principal.getUserCode();

        TemporaryBoard temporaryBoard=temporaryBoardRepository.findByTemporaryCode(temporaryCode);

        if(temporaryBoard.getUser().getUserCode()!=userCode) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        temporaryBoardRepository.deleteById(temporaryCode);

        return true;
    }

    @Override
    public boolean addViews(int boardCode) {
        Board board=boardRepository.findByCode(boardCode);
        if(board==null) {
            return false;
        }
        int viewCount=board.getViewCount()+1;
        board.viewCount(viewCount);
        board=boardRepository.save(board);

        return board.getViewCount()==viewCount;
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder
        File uploadFolder = new File(uploadPath, folderPath);

        if (uploadFolder.exists() == false) {
            uploadFolder.mkdirs();
        }

        return folderPath;
    }

    @Operation(summary="파일 조회",description="로그인한 사용자는 파일을 조회 할 수 있습니다.")
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) {
        log.info("fileName : " + fileName);

        ResponseEntity<byte[]> result = null;
        log.info("up result : " + result);

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("file : " + file);


            HttpHeaders header = new HttpHeaders();

            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            log.info("header : " + header);

            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            log.info("result : " + result);
        } catch (Exception e) {
            log.info("check");
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("result : " + result);
        return result;
    }




}

package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.BoardComment;
import com.energizor.restapi.board.entity.InterestBoard;
import com.energizor.restapi.board.entity.User;
import com.energizor.restapi.board.repository.BoardCommentRepository;
import com.energizor.restapi.board.repository.BoardRepository;
import com.energizor.restapi.board.repository.InterestBoardRepository;
import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.board.repository.BoardUserRepository;
import com.energizor.restapi.users.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardUserRepository userRepository;
    private final InterestBoardRepository interestBoardRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<BoardDTO> findAllList(Criteria cri, int boardTypeCode) {
        log.info("[BoardService] findAllList start=================");
        int index=cri.getPageNum()-1;
        int count=cri.getAmount();
        Pageable paging= PageRequest.of(index,count, Sort.by("boardCode").descending());

        Page<Board> result=boardRepository.findByBoardTypeCode(boardTypeCode,paging);
        /* Entity -> DTO로 변환 */
//        Page<BoardDTO> boardList=result.map(board->modelMapper.map(board,BoardDTO.class));
        Page<BoardDTO> boardList=result.map(board->{
            BoardDTO boardDTO=modelMapper.map(board,BoardDTO.class);

            // user의 team정보가 있을 경우에만 teamName 설정
            if(board.getUser()!=null && board.getUser().getTeam()!=null) {
                boardDTO.setTeamName(board.getUser().getTeam().getTeamName());
            }
            if(board.getUser()!=null && board.getUser().getTeam()!=null) {
                boardDTO.setDeptName(board.getUser().getTeam().getDepartment().getDeptName());
            }
            return boardDTO;
        });


        log.info("[BoardService] findAllList End======================");
        log.info("boardList : "+boardList);
        return boardList;
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
            boardDTO.setDeptName(board.getUser().getTeam().getDepartment().getDeptName());
        }

        log.info("[BoardService] findDetailBoard ======================");

        return boardDTO;
    }

    @Override
    public BoardDTO register(BoardDTO boardDTO) {
        log.info("[BoardService] register start===============");

        Board insertBoard=modelMapper.map(boardDTO,Board.class);
        Board savedBoard=boardRepository.save(insertBoard);

        BoardDTO savedBoardDTO=modelMapper.map(savedBoard, BoardDTO.class);

        log.info("[BoardService] register end ==================");
        return savedBoardDTO;
    }

    @Transactional
    @Override
    public String update(BoardDTO boardDTO) {

        log.info("[BoardService] update start =================");

        Optional<Board> result= Optional.ofNullable(boardRepository.findByCode(boardDTO.getBoardCode()));

        if(result.isPresent()) {
            Board board=result.get();
            board.title(boardDTO.getTitle());
            board.content(boardDTO.getContent()).build();
        }

        return null;
    }

    @Transactional
    @Override
    public BoardDTO delete(int boardCode) {
        log.info("[BoardService] delete start =================");

        Optional<Board> boardResult= Optional.ofNullable(boardRepository.findByCode(boardCode));
        LocalDateTime dateTime=LocalDateTime.now();

        if(boardResult.isPresent()) {
            Board board=boardResult.get();

            Optional<List<BoardComment>> commentResult=boardCommentRepository.findByBoardCode(boardResult.get().getBoardCode());

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
        return null;
    }

    @Override
    public BoardCommentDTO findComment(int boardCode) {

        log.info("[BoardService] findComment start ======================");
        BoardComment boardComment=boardCommentRepository.findByCodeWithComment(boardCode);
        BoardCommentDTO boardCommentDTO=modelMapper.map(boardComment, BoardCommentDTO.class);


        if(boardComment.getBoard()!=null) {
            boardCommentDTO.setDeptName(boardComment.getBoard().getUser().getTeam().getDepartment().getDeptName());
            boardCommentDTO.setTeamName(boardComment.getBoard().getUser().getTeam().getTeamName());
            boardCommentDTO.setUserName(boardComment.getBoard().getUser().getUserName());
        }



        return boardCommentDTO;
    }

    @Override
    public BoardCommentDTO registerComment(BoardCommentDTO boardCommentDTO) {
        log.info("[BoardService] registerComment start===============");

        BoardComment insertBoardComment=modelMapper.map(boardCommentDTO,BoardComment.class);
        BoardComment savedBoardComment=boardCommentRepository.save(insertBoardComment);

        BoardCommentDTO savedBoardCommentDTO=modelMapper.map(savedBoardComment,BoardCommentDTO.class);

        log.info("[BoardService] register end ==================");
        return savedBoardCommentDTO;
    }

    @Transactional
    @Override
    public String updateComment(BoardCommentDTO boardCommentDTO) {

        log.info("[BoardService] updateComment start ================ ");

        Optional<BoardComment> result= Optional.ofNullable(boardCommentRepository.findByCommentCode(boardCommentDTO.getCommentCode()));


        if(result.isPresent()) {
            BoardComment boardComment=result.get();
            boardComment.commentContent(boardCommentDTO.getCommentContent()).build();
        }

        return "댓글 수정 성공";
    }

    @Override
    public BoardCommentDTO deleteComment(int commentCode) {

        log.info("[BoardService] deleteComment start =================");
        Optional<BoardComment> result= Optional.ofNullable(boardCommentRepository.findByCommentCode(commentCode));

        BoardComment boardCommentEntity=null;
        LocalDateTime dateTime=LocalDateTime.now();
        if(result.isPresent()) {
            BoardComment boardComment = result.get();

            boardComment.changeReplyDeleteDate(dateTime);
            boardCommentEntity=boardCommentRepository.save(boardComment);

        }
        BoardCommentDTO response=modelMapper.map(boardCommentEntity,BoardCommentDTO.class);

        return response;
    }

    @Override
    public InterestBoardDTO registerInterestBoard(int boardCode, int userCode) {
        User user=userRepository.findByCode(userCode);
        Board board=boardRepository.findByCode(boardCode);

        InterestBoard interestBoard=new InterestBoard();
        interestBoard.user(user);
        interestBoard.board(board);

        InterestBoard interestEntity=interestBoardRepository.save(interestBoard);
        InterestBoardDTO interestDTO=modelMapper.map(interestEntity,InterestBoardDTO.class);

        return interestDTO;

    }

    @Transactional
    @Override
    public InterestBoardDTO deleteInterestBoard(int interestCode, int ownerCode) {

        Optional<InterestBoard> result=interestBoardRepository.findByInterestCode(interestCode);
        LocalDateTime dateTime=LocalDateTime.now();
        InterestBoard interestBoardEntity=null;

        if(result.isPresent()) {
            InterestBoard interestBoard = result.get();

            interestBoard.changeReplyDeleteDate(dateTime);
            interestBoardEntity=interestBoardRepository.save(interestBoard);

        }
        InterestBoardDTO response=modelMapper.map(interestBoardEntity,InterestBoardDTO.class);

        return response;
    }

    @Override
    public PageResultDTO<InterestBoardDTO, Object[]> findInterestBoardList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO : "+pageRequestDTO);

        Function<Object[],InterestBoardDTO> fn=(en->interestEnntityToDto((InterestBoard)en[0],(Board)en[1],(User)en[2],(Long)en[3]));

        Page<Object[]> result=interestBoardRepository.findInterestWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("interestCode").descending())
        );

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public InterestBoardDTO findDetailInterestBoard(int interestCode) {

//        Object result=interestBoardRepository.findDetailByInterestCode(interestCode);
//
//        String res=result.toString();
//        log.info("result : "+result);

        List<InterestBoardDTO> interestBoardDTOS=interestBoardRepository.findDetailByInterestCode(interestCode);

        InterestBoardDTO interestBoardDTO=new InterestBoardDTO();
        for(InterestBoardDTO dto:interestBoardDTOS) {
            int iCode=dto.getInterestCode();
            int boardCode=dto.getBoardCode();
            int userCode=dto.getUserCode();
            String userName=dto.getUserName();
            String title=dto.getTitle();
            String content=dto.getContent();
            int viewCount=dto.getViewCount();
            String teamName=dto.getTeamName();
            String deptName=dto.getDeptName();
            LocalDateTime registerDate=dto.getRegisterDate();
            LocalDateTime updateDate=dto.getUpdateDate();
            LocalDateTime deleteDate=dto.getDeleteDate();

            interestBoardDTO.setInterestCode(iCode);
            interestBoardDTO.setBoardCode(boardCode);
            interestBoardDTO.setUserCode(userCode);
            interestBoardDTO.setUserName(userName);
            interestBoardDTO.setTitle(title);
            interestBoardDTO.setContent(content);
            interestBoardDTO.setViewCount(viewCount);
            interestBoardDTO.setTeamName(teamName);
            interestBoardDTO.setDeptName(deptName);
            interestBoardDTO.setRegisterDate(registerDate);
            interestBoardDTO.setUpdateDate(updateDate);
            interestBoardDTO.setDeleteDate(deleteDate);

        }




//            return result.map(objects-> {
//
//                int iCode=(Integer)objects[0];
//                int boardCode=(int)(objects[1]);
//                int userCode=(int)(objects[2]);
//                String userName=(String)objects[3];
//                String title=(String)objects[4];
//                String content=(String)objects[5];
//                int viewCount=(int)(objects[6]);
//                String teamName=(String)objects[7];
//                String deptName=(String)objects[8];
//                LocalDateTime registerDate=(LocalDateTime)objects[9];
//                LocalDateTime updateDate=(LocalDateTime)objects[10];
//                LocalDateTime deleteDate=(LocalDateTime)objects[11];
//
//                InterestBoardDTO interestBoardDTO=new InterestBoardDTO();
//                interestBoardDTO.setInterestCode(iCode);
//                interestBoardDTO.setBoardCode(boardCode);
//                interestBoardDTO.setUserCode(userCode);
//                interestBoardDTO.setUserName(userName);
//                interestBoardDTO.setTitle(title);
//                interestBoardDTO.setContent(content);
//                interestBoardDTO.setViewCount(viewCount);
//                interestBoardDTO.setTeamName(teamName);
//                interestBoardDTO.setDeptName(deptName);
//                interestBoardDTO.setRegisterDate(registerDate);
//                interestBoardDTO.setUpdateDate(updateDate);
//                interestBoardDTO.setDeleteDate(deleteDate);
//
//
//                return interestBoardDTO;
//            }).orElseThrow(()->new EntityNotFoundException("\"InterestBoard not found with interestCode: \"" + interestCode));


        return interestBoardDTO;

    }

    @Override
    public PageResultDTO findTemporaryBoardList(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public Object findDetailTemporaryBoard(int temporaryCode) {
        return null;
    }

    @Override
    public Object deleteTemporaryBoard(int temporaryCode, UserDTO principal) {
        return null;
    }

    @Override
    public Object findAllList(PageRequestDTO pageRequestDTO) {
        return null;
    }


}

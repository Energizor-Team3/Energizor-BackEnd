package com.energizor.restapi.board.controller;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;

@Tag(name="게시판")
@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin("*")
public class BoardController {

    @Value("${board.upload.path}")
    private String uploadPath;

    private final BoardService boardService;

//    @Operation(summary="파일 업로드",description="로그인한 사용자는 글 작성 시에 파일을 업로드 할 수 있습니다.")
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("uploadFiles") MultipartFile[] uploadFiles,@RequestParam("boardCode")int boardCode) {
//        log.info("start");
//
//        List<UploadResultDTO> resultDTOList = boardService.uploadFile(uploadFiles,boardCode);
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"파일 업로드 성공",resultDTOList));
//    }


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



    @Operation(summary="파일 삭제",description="로그인한 사용자는 업로드 한 파일을 삭제할 수 있습니다.")
    @Transactional
    @DeleteMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(@RequestParam("fileName")String fileName) {

        String srcFileName = null;

        try {

            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("file : " + file);
            boolean result = file.delete();
            if (!result) return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

            File thumbnail = new File(file.getParent(), "s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary="게시글 조회",description="로그인한 사용자는 게시글을 조회할 수 있습니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> findAllList(PageRequestDTO pageRequestDTO, @AuthenticationPrincipal UserDTO principal,@RequestParam("boardTypeCode")int boardTypeCode) {

        System.out.println("boardTypeCode :"+boardTypeCode);

        log.info("[BoardController] boardTypeCode : "+boardTypeCode);
        log.info("pageRequestDTO : "+pageRequestDTO);
        pageRequestDTO.setBoardTypeCode(boardTypeCode);

        log.info("pageRequestDTO : " + pageRequestDTO);
        PageResultDTO response  =boardService.findAllList(pageRequestDTO);
        log.info("response : "+response);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 조회 성공",response));


    }

    @Operation(summary="게시글 상세 조회",description="로그인한 사용자는 게시글을 상세 조회할 수 있습니다.")
    @GetMapping("list/detail/{boardCode}")
    public ResponseEntity<ResponseDTO> findDetailBoard(@PathVariable(name="boardCode") int boardCode,@AuthenticationPrincipal UserDTO principal) {

        System.out.println("!!!!!!boardCode : "+boardCode);
        if(!boardService.addViews(boardCode)){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시판 상세글 조회 성공",boardService.findDetailBoard(boardCode)));
    }

    @Operation(summary="게시글 등록",description="로그인한 사용자는 게시글을 등록할 수 있습니다.")
    @PostMapping("/register")
//    public ResponseEntity<ResponseDTO> register(@RequestBody
//                                                    BoardDTO boardDTO,@AuthenticationPrincipal UserDTO principal,@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
    public ResponseEntity<ResponseDTO> register(@RequestParam("title") String title, @RequestParam("boardTypeCode") int boardTypeCode, @RequestParam(value = "uploadFiles", required = false) MultipartFile[] uploadFiles, @RequestParam("content") String content, @RequestParam(value = "isTemporaryOpt", required = false) Boolean temporaryOpt, @AuthenticationPrincipal UserDTO principal) {

        System.out.println("title = " + title);
        System.out.println("boardTypeCode = " + boardTypeCode);
        System.out.println("content = " + content);
        System.out.println("temporaryOpt = " + temporaryOpt);
        if(temporaryOpt == null) temporaryOpt = false;

//        log.info("boardDTO : "+boardDTO);
//        log.info("principal : "+principal);


        if (uploadFiles == null) {
            uploadFiles = new MultipartFile[0];
        }

//        if (temporaryOpt == null) {
//            temporaryOpt = true;
//        }

//        BoardDTO boardDTO = new BoardDTO(null, title, content, 0, LocalDateTime.now(), LocalDateTime.now(), null, principal.getUserRank(), principal.getUserName(), null, null, principal.getUserCode(), boardTypeCode, false, uploadFiles);
        CreateBoardDTO createBoardDTO = new CreateBoardDTO(
                title,
                content,
                boardTypeCode,
                temporaryOpt
        );

        System.out.println("hi");
//        return "hi";
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 등록 성공",boardService.register(createBoardDTO,principal,uploadFiles)));

    }

    @Operation(summary="게시글 수정",description="게시글을 작성한 작성자는 게시글을 수정할 수 있습니다.")
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody UpdateBoardDTO updateBoardDTO,@AuthenticationPrincipal UserDTO principal) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 수정 성공",boardService.update(updateBoardDTO,principal)));
    }

    @Operation(summary="게시글 삭제",description="게시글을 작성한 작성자는 게시글을 삭제할 수 있습니다.")
    @DeleteMapping("/delete/{boardCode}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable(name="boardCode") int boardCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 삭제 성공",boardService.delete(boardCode,principal)));

    }

    @Operation(summary="댓글 조회",description="로그인한 사용자는 댓글을 조회할 수 있습니다.")
    @GetMapping("/comment/{boardCode}")
    public ResponseEntity<ResponseDTO> findComment(@PathVariable(name="boardCode") int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 조회 성공",boardService.findComment(boardCode)));
    }

    @Operation(summary="댓글 등록",description="로그인한 사용자는 댓글을 등록할 수 있습니다.")
    @PostMapping("/comment/register")
    public ResponseEntity<ResponseDTO> registerComment(@RequestBody BoardCommentDTO boardCommentDTO,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 등록 성공",boardService.registerComment(boardCommentDTO,principal)));
    }

    @Operation(summary="댓글 수정",description="댓글을 작성한 작성자는 댓글을 수정할 수 있습니다.")
    @PatchMapping("/comment/update")
    public ResponseEntity<ResponseDTO> updateComment(@RequestBody UpdateBoardCommentDTO updateBoardCommentDTO ,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 수정 성공",boardService.updateComment(updateBoardCommentDTO,principal)));
    }

    @Operation(summary="댓글 삭제",description="댓글을 작성한 작성자는 댓글을 삭제할 수 있습니다.")
    @DeleteMapping("/comment/delete/{commentCode}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable(name="commentCode") int commentCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 삭제 성공",boardService.deleteComment(commentCode,principal)));
    }

    @Operation(summary="게시글 파일 조회",description="로그인한 사용자는 게시글 파일을 조회할 수 있습니다.")
    @GetMapping("/file/list/{boardCode}")
    public ResponseEntity<ResponseDTO> findBoardFile(@PathVariable(name="boardCode")int boardCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 파일 조회 성공",boardService.findBoardFile(boardCode)));
    }

    @Operation(summary="관심게시판 조회",description="로그인한 사용자는 관심게시글로 등록한 게시글을 조회할 수 있습니다.")
    @GetMapping("/interest/list")
    public ResponseEntity<ResponseDTO> findInterestBoardList (PageRequestDTO pageRequestDTO,@AuthenticationPrincipal UserDTO principal) {

        log.info("list:"+pageRequestDTO);

        PageResultDTO response=boardService.findInterestBoardList(pageRequestDTO);
        log.info("response : "+response);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 조회 성공",response));
    }

    @Operation(summary="관심게시판 상세 조회",description="로그인한 사용자는 관심게시글을 상세 조회할 수 있습니다.")
    @GetMapping("/interest/detail/{interestCode}")
    public ResponseEntity<ResponseDTO> findDetailInterestBoard(@PathVariable(name="interestCode")int interestCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 상세 조회 성공",boardService.findDetailInterestBoard(interestCode)));
    }

    @Operation(summary="관심게시글 등록",description="로그인한 사용자는 관심게시글을 등록할 수 있습니다.")
    @PostMapping("/interest/register")
    public ResponseEntity<ResponseDTO> registerInterestBoard(@RequestBody BoardDTO boardDTO, @AuthenticationPrincipal UserDTO principal) {

        log.info("owner : "+principal);
        int boardCode=boardDTO.getBoardCode();
        int ownerCode=principal.getUserCode();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 등록 성공",boardService.registerInterestBoard(boardCode,ownerCode)));
    }

    @Operation(summary="관심게시글 삭제",description="로그인한 사용자는 관심게시글을 삭제할 수 있습니다.")
    @DeleteMapping("/interest/delete/{interestCode}")
    public ResponseEntity<ResponseDTO> deleteInterestBoard(@PathVariable(name="interestCode") int interestCode,@AuthenticationPrincipal UserDTO owner) {

        int ownerCode=owner.getUserCode();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 삭제",boardService.deleteInterestBoard(interestCode,ownerCode)));
    }

    @Operation(summary="임시게시글 조회",description="로그인한 사용자는 임시게시글을 조회할 수 있습니다.")
    @GetMapping("/temporary/list")
    public ResponseEntity<ResponseDTO> findTemporaryBoardList(PageRequestDTO pageRequestDTO,@AuthenticationPrincipal UserDTO principal) {
        log.info("pageRequestDTO : "+pageRequestDTO);

        PageResultDTO response=boardService.findTemporaryBoardList(pageRequestDTO);
        log.info("response : "+response);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장함 조회 성공",response));

    }

    @Operation(summary="임시게시글 상세 조회",description="로그인한 사용자는 임시게시글을 상세 조회할 수 있습니다.")
    @GetMapping("temporary/detail/{temporaryCode}")
    public ResponseEntity<ResponseDTO> findDetailTemporaryBoard(@PathVariable(name="temporaryCode")int temporaryCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장함 상세 조회 성공",boardService.findDetailTemporaryBoard(temporaryCode)));
    }

    @Operation(summary="임시게시글 삭제",description="로그인한 사용자는 임시게시글을 삭제할 수 있습니다.")
    @DeleteMapping("/temporary/delete/{temporaryCode}")
    public ResponseEntity<ResponseDTO> deleteTemporaryBoard(@PathVariable(name="temporaryCode")int temporaryCode, @AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장글 삭제 성공",boardService.deleteTemporaryBoard(temporaryCode,principal)));
    }




}
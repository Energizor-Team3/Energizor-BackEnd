package com.energizor.restapi.board.controller;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.service.BoardService;
import com.energizor.restapi.common.Criteria;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import com.energizor.restapi.common.PageDTO;
import com.energizor.restapi.common.PagingResponseDTO;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/upload")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        log.info("upload start : "+uploadFiles);

        List<UploadResultDTO> resultDTOList=new ArrayList<>();

        for(MultipartFile uploadFile:uploadFiles) {

            // 이미지 파일만 업로드 가능
            if(uploadFile.getContentType().startsWith("image")==false) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // 실제 파일 이름 IEsk Edge는 전체 경로가 들어오므로
            String originalName=uploadFile.getOriginalFilename();
            String fileName=originalName.substring(originalName.lastIndexOf("\\")+1);

            // 날짜 폴더 생성
            String folderPath=makeFolder();
            log.info("folderPath : "+folderPath);

            // UUID
            String uuid= UUID.randomUUID().toString();
            log.info("uuid : "+uuid);

            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName=uploadPath+ File.separator+folderPath+File.separator+uuid+"_"+fileName;
            log.info("saveName : "+saveName);

            Path savePath= Paths.get(saveName);
            log.info("savePath : "+savePath);

            try{
                // 실제 이미지 저장
                uploadFile.transferTo(savePath);

                // 섬네일 생성
                String thumbnailSaveName=uploadPath+File.separator+folderPath+File.separator+"s_"+uuid+"_"+fileName;

                // 섬네일 파일 이름 중간에 s_시작하는점 기억해두기
                File thumbnailFile=new File(thumbnailSaveName);

                // 섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);
    }

    private String makeFolder() {
        String str= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath=str.replace("/",File.separator);

        File uploadFolder=new File(uploadPath,folderPath);

        if(uploadFolder.exists()==false) {
            uploadFolder.mkdirs();
        }

        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) {
        log.info("display start : "+fileName);

        ResponseEntity<byte[]> result=null;

        try {
            String srcFileName= URLDecoder.decode(fileName,"UTF-8");
            log.info("fileName : "+srcFileName);

            File file=new File(uploadPath+File.separator+srcFileName);
            log.info("file : "+file);

            HttpHeaders header=new HttpHeaders();

            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            log.info("header : "+header);

            // 파일 데이터 처리
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
            log.info("result : "+result);
        } catch(Exception e) {
            log.info("check");
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  result;
    }

    @Transactional
    @DeleteMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(@RequestParam("fileName")String fileName) {

        String srcFileName=null;

        try {
            srcFileName=URLDecoder.decode(fileName,"UTF-8");
            File file=new File(uploadPath+File.separator+srcFileName);
            log.info("file : "+file);

            boolean result=file.delete();
            if(!result) return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);

            File thumbnail=new File(file.getParent(),"s_"+file.getName());

            result=thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary="게시글 조회",description="로그인한 사용자는 게시글을 조회할 수 있습니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> findAllList(@RequestParam(name = "boardTypeCode") int boardTypeCode,PageRequestDTO pageRequestDTO,@AuthenticationPrincipal UserDTO principal) {

        log.info("[BoardController] boardTypeCode : "+boardTypeCode);
        log.info("pageRequestDTO : "+pageRequestDTO);
        pageRequestDTO.setBoardTypeCode(boardTypeCode);

        PageResultDTO response=boardService.findAllList(pageRequestDTO);
        log.info("response : "+response);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 조회 성공",response));


    }

    @Operation(summary="게시글 상세 조회",description="로그인한 사용자는 게시글을 상세 조회할 수 있습니다.")
    @GetMapping("list/detail/{boardCode}")
    public ResponseEntity<ResponseDTO> findDetailBoard(@PathVariable(name="boardCode") int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시판 상세글 조회 성공",boardService.findDetailBoard(boardCode)));
    }

    @Operation(summary="게시글 등록",description="로그인한 사용자는 게시글을 등록할 수 있습니다.")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody BoardDTO boardDTO,@AuthenticationPrincipal UserDTO principal) {

        log.info("boardDTO : "+boardDTO);
        log.info("principal : "+principal);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 등록 성공",boardService.register(boardDTO,principal)));

    }

    @Operation(summary="게시글 수정",description="게시글을 작성한 작성자는 게시글을 수정할 수 있습니다.")
    @PatchMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody BoardDTO boardDTO,@AuthenticationPrincipal UserDTO principal) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 수정 성공",boardService.update(boardDTO,principal)));
    }

    @Operation(summary="게시글 삭제",description="게시글을 작성한 작성자는 게시글을 삭제할 수 있습니다.")
    @PatchMapping("/delete/{boardCode}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable int boardCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 삭제 성공",boardService.delete(boardCode,principal)));

    }

    @Operation(summary="댓글 조회",description="로그인한 사용자는 댓글을 조회할 수 있습니다.")
    @GetMapping("/comment/{boardCode}")
    public ResponseEntity<ResponseDTO> findComment(@PathVariable int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 조회 성공",boardService.findComment(boardCode)));
    }

    @Operation(summary="댓글 등록",description="로그인한 사용자는 댓글을 등록할 수 있습니다.")
    @PostMapping("/comment/register")
    public ResponseEntity<ResponseDTO> registerComment(@RequestBody BoardCommentDTO boardCommentDTO,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 등록 성공",boardService.registerComment(boardCommentDTO,principal)));
    }

    @Operation(summary="댓글 수정",description="댓글을 작성한 작성자는 댓글을 수정할 수 있습니다.")
    @PatchMapping("/comment/update")
    public ResponseEntity<ResponseDTO> updateComment(@RequestBody BoardCommentDTO boardCommentDTO,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 수정 성공",boardService.updateComment(boardCommentDTO,principal)));
    }

    @Operation(summary="댓글 삭제",description="댓글을 작성한 작성자는 댓글을 삭제할 수 있습니다.")
    @PatchMapping("/comment/delete/{commentCode}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable int commentCode,@AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 삭제 성공",boardService.deleteComment(commentCode,principal)));
    }

    @Operation(summary="관심게시판 조회",description="로그인한 사용자는 관심게시글로 등록한 게시글을 조회할 수 있습니다.")
    @GetMapping("/interest/list")
    public ResponseEntity<ResponseDTO> findInterestBoardList (PageRequestDTO pageRequestDTO) {

       log.info("list:"+pageRequestDTO);

       PageResultDTO response=boardService.findInterestBoardList(pageRequestDTO);
       log.info("response : "+response);

       return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 조회 성공",response));
    }

    @Operation(summary="관심게시판 상세 조회",description="로그인한 사용자는 관심게시글을 상세 조회할 수 있습니다.")
    @GetMapping("/interest/detail/{interestCode}")
    public ResponseEntity<ResponseDTO> findDetailInterestBoard(@PathVariable(name="interestCode")int interestCode) {

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
    @PatchMapping("/interest/delete")
    public ResponseEntity<ResponseDTO> deleteInterestBoard(@RequestBody InterestBoardDTO interestBoardDTO,@AuthenticationPrincipal UserDTO owner) {

        int interestCode=interestBoardDTO.getInterestCode();
        int ownerCode=owner.getUserCode();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"관심게시글 삭제",boardService.deleteInterestBoard(interestCode,ownerCode)));
    }

    @Operation(summary="임시게시글 조회",description="로그인한 사용자는 임시게시글을 조회할 수 있습니다.")
    @GetMapping("/temporary/list")
    public ResponseEntity<ResponseDTO> findTemporaryBoardList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO : "+pageRequestDTO);

        PageResultDTO response=boardService.findTemporaryBoardList(pageRequestDTO);
        log.info("response : "+response);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장함 조회 성공",response));

    }

    @Operation(summary="임시게시글 상세 조회",description="로그인한 사용자는 임시게시글을 상세 조회할 수 있습니다.")
    @GetMapping("temporary/detail/{temporaryCode}")
    public ResponseEntity<ResponseDTO> findDetailTemporaryBoard(@PathVariable(name="temporaryCode")int temporaryCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장함 상세 조회 성공",boardService.findDetailTemporaryBoard(temporaryCode)));
    }

    @Operation(summary="임시게시글 조회",description="로그인한 사용자는 임시게시글을 삭제할 수 있습니다.")
    @DeleteMapping("/temporary/delete/{temporaryCode}")
    public ResponseEntity<ResponseDTO> deleteTemporaryBoard(@PathVariable(name="temporaryCode")int temporaryCode, @AuthenticationPrincipal UserDTO principal) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"임시저장글 삭제 성공",boardService.deleteTemporaryBoard(temporaryCode,principal)));
    }

}

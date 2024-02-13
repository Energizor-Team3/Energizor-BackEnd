package com.energizor.restapi.note.controller;

import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.common.PageDTO;
import com.energizor.restapi.common.PagingResponseDTO;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.note.dto.SendNoteDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "note 스웨거 연동")
@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController {


//    @GetMapping("/sendNote")
//    public ResponseEntity<ResponseDTO> selectSendNoteListWithPaging(
//            @RequestParam(name = "offset", defaultValue = "1") String offset){
//
//        log.info("selectSendNoteListWithPaging offset=======", offset);
//
//        Criteria cri = new Criteria(Integer.valueOf(offset),10);
//
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//
//        Page<SendNoteDTO> sendNoteList = noteController.selectSendNoteListWithPaging(cri);
//        pagingResponseDTO.setPageInfo((new PageDTO(cri, (int) sendNoteList.getTotalElements())));
//
//        log.info("selectSendNoteListWithPaging End========");
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO))
//
//    }

}

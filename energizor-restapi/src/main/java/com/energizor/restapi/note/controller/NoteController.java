package com.energizor.restapi.note.controller;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.note.dto.SendNoteDTO;
import com.energizor.restapi.note.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Note Controller")
@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /* 쪽지쓰기 */
    @PostMapping("/send-message")
    public ResponseEntity<ResponseDTO> insertSendNote(@RequestBody SendNoteDTO sendNoteDTO){

        System.out.println("쪽지쓰기 확인======="+ sendNoteDTO);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "쪽지 보내기 성공", noteService.insertSendNote(sendNoteDTO)));

    }



}

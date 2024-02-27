package com.energizor.restapi.note.service;
import com.energizor.restapi.note.dto.SendNoteDTO;
import com.energizor.restapi.note.entity.SendNote;
import com.energizor.restapi.note.repository.SendNoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NoteService {

    private final ModelMapper modelMapper;
    private final SendNoteRepository sendNoteRepository;


    public NoteService(ModelMapper modelMapper
            , SendNoteRepository sendNoteRepository) {
        this.modelMapper = modelMapper;
        this.sendNoteRepository = sendNoteRepository;
    }

    @Transactional
    public String insertSendNote(SendNoteDTO sendNoteDTO) {

        log.info("insertSendNote start ==============="+ sendNoteDTO);

        int result = 0;

        try {


            SendNote insertSendNote = modelMapper.map(sendNoteDTO, SendNote.class);

            sendNoteRepository.save(insertSendNote);

            result = 1;
        } catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertSendNote End=============");
        return (result > 0? "쪽지보내기 성공": "쪽지 보내기 실패");
    }
}

package com.energizor.restapi.contact.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.contact.dto.PersonalContactDTO;
import com.energizor.restapi.contact.service.ContactService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@Log4j2
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /* 회사 연락처 목록 조회 */
    @GetMapping("/company-list")
    public ResponseEntity<ResponseDTO> getCompanyList() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회사 연락처 목록 조회 성공!", contactService.findAllCompanyList()));
    }

    /* 개인 연락처 목록 조회*/
    @GetMapping("/personal-list/{userCode}")
    public ResponseEntity<ResponseDTO> getPersonalContactByUserCode(@PathVariable("userCode") int userCode) {
        List<PersonalContactDTO> personalContact = contactService.getPersonalContactByUserCode(userCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 연락처 목록 조회 성공!", personalContact));
    }

    /* 회사 연락처 상세 조회 */
    @GetMapping("/company-detail/{cpCode}")
    public ResponseEntity<ResponseDTO> selectCompany(@PathVariable int cpCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회사 연락처 상세 조회 성공!", contactService.selectCompany(cpCode)));
    }

    /* 개인 연락처 상세 조회 */
    @GetMapping("/personal-detail/{pcCode}")
    public ResponseEntity<ResponseDTO> selectPersonal(@PathVariable int pcCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 연락처 상세 조회 성공!", contactService.selectPersonal(pcCode)));
    }

    /* 개인 연락처 추가 */
    @PostMapping("/personal-insert")
    public ResponseEntity<ResponseDTO> insert(@RequestBody PersonalContactDTO personalContactDTO) {
        int userCode = personalContactDTO.getUserCode();
        PersonalContactDTO insert = contactService.insert(userCode, personalContactDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "개인 연락처 추가 성공!", insert);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /* 개인 연락처 수정 */
    @PutMapping("/personal-update/{pcCode}")
    public ResponseEntity<ResponseDTO> update(@PathVariable int pcCode, @RequestBody PersonalContactDTO personalContactDTO) {
        PersonalContactDTO update = contactService.update(pcCode, personalContactDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "개인 연락처 수정 성공!", update);

        return ResponseEntity.ok().body(responseDTO);
    }

    /* 개인 연락처 삭제 */
    @DeleteMapping("/personal-delete/{pcCode}")
    public ResponseEntity<PersonalContactDTO> delete(@PathVariable int pcCode) {
        PersonalContactDTO delete = contactService.delete(pcCode);
        if (delete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(delete);
        }
    }

}

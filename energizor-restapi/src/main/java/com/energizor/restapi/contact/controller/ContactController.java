package com.energizor.restapi.contact.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.contact.dto.PersonalContactDTO;
import com.energizor.restapi.contact.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contact Controller 스웨거 연동")
@RestController
@RequestMapping("/contact")
@Log4j2
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /* 회사 연락처 목록 조회 */
    @Operation(summary = "회사 연락처 목록 조회", description = "모든 회사 연락처 목록을 조회한다.")
    @GetMapping("/company-list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> getCompanyList() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회사 연락처 목록 조회 성공!", contactService.findAllCompanyList()));
    }

    /* 개인 연락처 목록 조회*/
    @Operation(summary = "개인 연락처 목록 조회", description = "로그인한 사원의 개인 연락처 목록을 조회한다.")
    @GetMapping("/personal-list/{userCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> getPersonalContactByUserCode(@PathVariable("userCode") int userCode) {
        List<PersonalContactDTO> personalContact = contactService.getPersonalContactByUserCode(userCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 연락처 목록 조회 성공!", personalContact));
    }

    /* 회사 연락처 상세 조회 */
    @Operation(summary = "회사 연락처 상세 조회", description = "회사 연락처 중 하나의 연락처를 상세 조회한다.")
    @GetMapping("/company-detail/{cpCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> selectCompany(@PathVariable int cpCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회사 연락처 상세 조회 성공!", contactService.selectCompany(cpCode)));
    }

    /* 개인 연락처 상세 조회 */
    @Operation(summary = "개인 연락처 상세 조회", description = "개인 연락처 중 하나의 연락처를 상세 조회한다.")
    @GetMapping("/personal-detail/{pcCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> selectPersonal(@PathVariable int pcCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 연락처 상세 조회 성공!", contactService.selectPersonal(pcCode)));
    }

    /* 개인 연락처 추가 */
    @Operation(summary = "개인 연락처 추가", description = "로그인한 사원의 개인 연락처 정보를 추가한다.")
    @PostMapping("/personal-insert")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> insert(@RequestBody PersonalContactDTO personalContactDTO) {
        int userCode = personalContactDTO.getUserCode();
        PersonalContactDTO insert = contactService.insert(userCode, personalContactDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "개인 연락처 추가 성공!", insert);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /* 개인 연락처 수정 */
    @Operation(summary = "개인 연락처 수정", description = "로그인한 사원의 개인 연락처 정보를 수정한다.")
    @PutMapping("/personal-update/{pcCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> update(@PathVariable int pcCode, @RequestBody PersonalContactDTO personalContactDTO) {
        PersonalContactDTO update = contactService.update(pcCode, personalContactDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "개인 연락처 수정 성공!", update);

        return ResponseEntity.ok().body(responseDTO);
    }

    /* 개인 연락처 삭제 */
    @Operation(summary = "개인 연락처 삭제", description = "로그인한 사원의 개인 연락처 정보를 삭제한다")
    @DeleteMapping("/personal-delete/{pcCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PersonalContactDTO> delete(@PathVariable int pcCode) {
        PersonalContactDTO delete = contactService.delete(pcCode);
        if (delete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(delete);
        }
    }

}

package com.energizor.restapi.approval.controller;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.service.ApprovalService;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/approval")
@Slf4j
public class ApprovalController {
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // 기안 문서 조회
    @GetMapping("/document")
    public ResponseEntity<ResponseDTO> selectDocument(@AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.findDocumentsByUserCode(userDTO)));
    }

    // 결재 대기 문서 조회
    @GetMapping("/inboxApproval")
    public ResponseEntity<ResponseDTO> inboxApproval(@AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.inBoxDocumentByUserCode(userDTO)));

    }
    // 결재하기
    @PutMapping("/approvement/{documentCode}")
    public ResponseEntity<String> approvement(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        System.out.println("documentCode = " + documentCode);
        System.out.println("userDTO = " + userDTO);

        String result = approvalService.approvement(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 반려하기
    @PutMapping("/rejection/{documentCode}")
    public ResponseEntity<String> rejection(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        System.out.println("documentCode = " + documentCode);
        System.out.println("userDTO = " + userDTO);

        String result = approvalService.rejection(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 상신 문서 회수
    @PutMapping("/withdraw/{documentCode}")
    public ResponseEntity<String> withdraw(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        String result = approvalService.withdraw(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 결재 진행 중인 문서 조회
    @GetMapping("/approvalProgress")
    public ResponseEntity<ResponseDTO> approvalProgress(@AuthenticationPrincipal UserDTO userDTO) {
        List<DocumentDTO> documents = approvalService.approvalProgress(userDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", documents));
    }

    // 결재 완료 문서 조회
    @GetMapping("/approvalComplete")
    public ResponseEntity<ResponseDTO> approvalComplete(@AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.approvalComplete(userDTO)));
    }

    // 반려 문서 조회
    @GetMapping("/rejection")
    public ResponseEntity<ResponseDTO> rejectionInOutbox(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.rejectionInOutbox(userDTO)));
    }

    // 회수 문서 조회
    @GetMapping("/withdraw")
    public ResponseEntity<ResponseDTO> withdrawInOutbox(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.withdrawInOutbox(userDTO)));
    }

    // 휴가 임시 저장
    @PostMapping("/saveApprovalDayOff")
    public ResponseEntity<ResponseDTO> saveApprovalDayOff(@ModelAttribute DayOffApplyDTO dayOffApplyDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalDayOff(dayOffApplyDTO, userDTO)));

    }
    // 교육 임시 저장
    @PostMapping("/saveApprovalEducation")
    public ResponseEntity<ResponseDTO> saveApprovalEducation(@ModelAttribute EducationDTO educationDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalEducation(educationDTO, userDTO)));

    }
    // 출장 임시 저장
    @PostMapping("/saveApprovalBusinessTrip")
    public ResponseEntity<ResponseDTO> saveApprovalBusinessTrip(@ModelAttribute BusinessTripDTO businessTripDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalBusinessTrip(businessTripDTO, userDTO)));

    }
    // 일반 임시 저장
    @PostMapping("/saveApprovalGeneral")
    public ResponseEntity<ResponseDTO> saveApprovalGeneral(@ModelAttribute GeneralDraftDTO generalDraftDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalGeneralDraft(generalDraftDTO, userDTO)));

    }

    // 임시 기안 문서 조회
    @GetMapping("/tempSaveDocument")
    public ResponseEntity<ResponseDTO> selectTempSaveDocument(@AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.findTempSaveDocument(userDTO)));
    }




    //기안추가
    @PostMapping("/dayOffApply")
    public ResponseEntity<ResponseDTO> insertDayOffApply(@ModelAttribute DayOffApplyDTO dayOffApplyDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal) throws IOException {
        System.out.println("principal=============================================================== = " + principal);
        System.out.println("file==============="+file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.insertDayOffApply(dayOffApplyDTO, file, principal)));
    }

    @PostMapping("/businessTrip")
    public ResponseEntity<ResponseDTO> insertBusinessTrip(@ModelAttribute BusinessTripDTO businessTripDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertBusinessTrip(businessTripDTO, file, principal)));
    }

    @PostMapping("/education")
    public ResponseEntity<ResponseDTO> insertEducation(@ModelAttribute EducationDTO educationDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertEducation(educationDTO, file, principal)));
    }

    @PostMapping("/generalDraft")
    public ResponseEntity<ResponseDTO> insertgeneralDraft(@ModelAttribute GeneralDraftDTO generalDraftDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertgeneralDraft(generalDraftDTO, file, principal)));
    }





}

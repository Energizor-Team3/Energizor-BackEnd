package com.energizor.restapi.approval.controller;

import com.energizor.restapi.approval.service.ApprovalService;
import com.energizor.restapi.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approval")
@Slf4j
public class ApprovalController {
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping("/document")
    public ResponseEntity<ResponseDTO> selectApprovalList(){
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalList()));
    }

    // 기안양식들 전체조회

    @GetMapping("/generalDraft")
    public ResponseEntity<ResponseDTO> selectGeneralDraft(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectGeneralDraft()));
    }
    @GetMapping("/education")
    public ResponseEntity<ResponseDTO> selectEducation(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectEducation()));
    }
    @GetMapping("/businessTrip")
    public ResponseEntity<ResponseDTO> selectBusinessTrip(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectBusinessTrip()));
    }
    @GetMapping("/dayOffApply")
    public ResponseEntity<ResponseDTO> selectDayOffApply(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectDayOffApply()));
    }

}

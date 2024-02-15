package com.energizor.restapi.approval.controller;

import com.energizor.restapi.approval.dto.BusinessTripDTO;
import com.energizor.restapi.approval.dto.DayOffApplyDTO;
import com.energizor.restapi.approval.dto.EducationDTO;
import com.energizor.restapi.approval.service.ApprovalService;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "전자결재 API")
@RestController
@RequestMapping("/approval")
@Slf4j
public class ApprovalController {
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }



    // 기안양식들 전체조회

    @Operation(summary = "일반 기안 조회")
    @GetMapping("/generalDraft")
    public ResponseEntity<ResponseDTO> selectGeneralDraft(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectGeneralDraft()));
    }
    @Operation(summary = "교육 기안 조회")
    @GetMapping("/education")
    public ResponseEntity<ResponseDTO> selectEducation(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectEducation()));
    }
    @Operation(summary = "출장 기안 조회")
    @GetMapping("/businessTrip")
    public ResponseEntity<ResponseDTO> selectBusinessTrip(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectBusinessTrip()));
    }
    @Operation(summary = "휴가 기안 조회")
    @GetMapping("/dayOffApply")
    public ResponseEntity<ResponseDTO> selectDayOffApply(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectDayOffApply()));
    }

    //기안추가

    @Operation(summary = "휴가 기안 등록")
    @PostMapping("/dayOffApply")
    public ResponseEntity<ResponseDTO> insertDayOffApply(@RequestBody DayOffApplyDTO dayOffApplyDTO,  @AuthenticationPrincipal UserDTO principal) {
        System.out.println("principal=============================================================== = " + principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertDayOffApply(dayOffApplyDTO, principal)));
    }

    @Operation(summary = "출장 기안 등록")
    @PostMapping("/businessTrip")
    public ResponseEntity<ResponseDTO> insertBusinessTrip(@RequestBody BusinessTripDTO businessTripDTO, @AuthenticationPrincipal UserDTO principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertBusinessTrip(businessTripDTO, principal)));
    }

    @Operation(summary = "교육 기안 등록")
    @PostMapping("/education")
    public ResponseEntity<ResponseDTO> insertEducation(@RequestBody EducationDTO educationDTO, @AuthenticationPrincipal UserDTO principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertEducation(educationDTO, principal)));
    }

    // 참조자, 결재자, 공유 문서 전체 조회

    @Operation(summary = "참조 문서 조회")
    @GetMapping("/reference")
    public ResponseEntity<ResponseDTO> selectReference(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectReference()));
    }
    @Operation(summary = "공유 문서 조회")
    @GetMapping("/sharedDocument")
    public ResponseEntity<ResponseDTO> selectSharedDocument(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectSharedDocument()));
    }
    @Operation(summary = "결재자 조회")
    @GetMapping("/approvalLine")
    public ResponseEntity<ResponseDTO> selectApprovalLine(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalLine()));
    }

    @Operation(summary = "문서 조회")
    @GetMapping("/document")
    public ResponseEntity<ResponseDTO> selectDocument(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectDocument()));
    }

    // 기타(댓글, 결재위임), 첨부파일 전체조회

    @Operation(summary = "댓글 조회")
    @GetMapping("/approvalComment")
    public ResponseEntity<ResponseDTO> selectApprovalComment(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalComment()));
    }
    @Operation(summary = "첨부 파일 조회")
    @GetMapping("/approvalFile")
    public ResponseEntity<ResponseDTO> selectApprovalFile(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalFile()));
    }
    @Operation(summary = "위임자 조회")
    @GetMapping("/proxyApproval")
    public ResponseEntity<ResponseDTO> selectProxyApproval(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectProxyApproval()));
    }



}

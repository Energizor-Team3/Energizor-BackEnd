package com.energizor.restapi.approval.controller;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.service.ApprovalService;
import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.common.PageDTO;
import com.energizor.restapi.common.PagingResponseDTO;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "전자결재API")
@RestController
@RequestMapping("/approval")
@Slf4j
public class ApprovalController {
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

//    //페이징 처리
//    @GetMapping("/documentPaging")
//    public ResponseEntity<ResponseDTO> selectProductListWithPaging(
//            @RequestParam(name = "offset", defaultValue = "1") String offset){
//
//        log.info("[ProductController] selectProductListWithPaging Start ============ ");
//        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);
//
//        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
//
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//
//
//
//
//        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
//        Page<DocumentDTO> documentList = approvalService.selectProductListWithPaging(cri);
//        pagingResponseDTO.setData(documentList);
//        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
//
//
//
//
//        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));
//
//        log.info("[ProductController] selectProductListWithPaging End ============ ");
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
//    }

    // 기안 문서 조회
    @Operation(summary = "기안한 문서 조회")
    @GetMapping("/document")
    public ResponseEntity<ResponseDTO> selectDocument(@AuthenticationPrincipal UserDTO userDTO, @RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[ProductController] selectProductListWithPaging Start ============ ");
        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        Page<DocumentDTO> documentList = approvalService.findDocumentsByUserCode(userDTO, cri);
        pagingResponseDTO.setData(documentList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */


        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));

        log.info("[ProductController] selectProductListWithPaging End ============ ");



        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", pagingResponseDTO));
    }

    // 결재 대기 문서 조회
    @Operation(summary = "결재 해야할 문서 조회")
    @GetMapping("/inboxApproval")
    public ResponseEntity<ResponseDTO> inboxApproval(@AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.inBoxDocumentByUserCode(userDTO)));

    }
    // 결재하기
    @Operation(summary = "문서 결재")
    @PutMapping("/approvement/{documentCode}")
    public ResponseEntity<String> approvement(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        System.out.println("documentCode = " + documentCode);
        System.out.println("userDTO = " + userDTO);

        String result = approvalService.approvement(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 문서별 결재, 참조 자 조회
    @Operation(summary = "문서별 결재자 조회")
    @GetMapping("/selectApprovalLine/{documentCode}")
    public ResponseEntity<ResponseDTO> selectApprovalLine(@PathVariable int documentCode) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalLine(documentCode)));

    }

    @Operation(summary = "문서별 참조자 조회")
    @GetMapping("/selectApprovalRf/{documentCode}")
    public ResponseEntity<ResponseDTO> selectApprovalRf(@PathVariable int documentCode) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalRf(documentCode)));

    }


    // 반려하기
    @Operation(summary = "문서 반려")
    @PutMapping("/rejection/{documentCode}")
    public ResponseEntity<String> rejection(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        System.out.println("documentCode = " + documentCode);
        System.out.println("userDTO = " + userDTO);

        String result = approvalService.rejection(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 상신 문서 회수
    @Operation(summary = "결재를 아무도 하지 않은 문서 철회")
    @PutMapping("/withdraw/{documentCode}")
    public ResponseEntity<String> withdraw(@PathVariable int documentCode, @AuthenticationPrincipal UserDTO userDTO){
        String result = approvalService.withdraw(documentCode, userDTO);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    // 결재 진행 중인 문서 조회
    @Operation(summary = "결재 진행중인 문서")
    @GetMapping("/approvalProgress")
    public ResponseEntity<ResponseDTO> approvalProgress(@AuthenticationPrincipal UserDTO userDTO) {
        List<DocumentDTO> documents = approvalService.approvalProgress(userDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", documents));
    }

    // 결재 완료 문서 조회
    @Operation(summary = "결재 완료 문서")
    @GetMapping("/approvalComplete")
    public ResponseEntity<ResponseDTO> approvalComplete(@AuthenticationPrincipal UserDTO userDTO, @RequestParam(name = "offset", defaultValue = "1") String offset) {

            log.info("[ProductController] selectProductListWithPaging Start ============ ");
            log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

            Criteria cri = new Criteria(Integer.valueOf(offset), 10);

            PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

            /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
            Page<DocumentDTO> documentList = approvalService.approvalComplete(userDTO, cri);
            pagingResponseDTO.setData(documentList);
            /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
            pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    // 반려 문서 조회
    @Operation(summary = "반려된 문서 조회")
    @GetMapping("/rejection")
    public ResponseEntity<ResponseDTO> rejectionInOutbox(@AuthenticationPrincipal UserDTO userDTO,  @RequestParam(name = "offset", defaultValue = "1") String offset) {
        log.info("[ProductController] selectProductListWithPaging Start ============ ");
        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        Page<DocumentDTO> documentList = approvalService.rejectionInOutbox(userDTO, cri);
        pagingResponseDTO.setData(documentList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    // 회수 문서 조회
    @Operation(summary = "철회한 문서 조회")
    @GetMapping("/withdraw")
    public ResponseEntity<ResponseDTO> withdrawInOutbox(@AuthenticationPrincipal UserDTO userDTO, @RequestParam(name = "offset", defaultValue = "1") String offset) {
        log.info("[ProductController] selectProductListWithPaging Start ============ ");
        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        Page<DocumentDTO> documentList = approvalService.withdrawInOutbox(userDTO, cri);
        pagingResponseDTO.setData(documentList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    // 휴가 임시 저장
    @Operation(summary = "휴가 기안 임시 저장")
    @PostMapping("/saveApprovalDayOff")
    public ResponseEntity<ResponseDTO> saveApprovalDayOff(@ModelAttribute DayOffApplyDTO dayOffApplyDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalDayOff(dayOffApplyDTO, userDTO)));

    }
    // 교육 임시 저장
    @Operation(summary = "교육 기안 임시 저장")
    @PostMapping("/saveApprovalEducation")
    public ResponseEntity<ResponseDTO> saveApprovalEducation(@ModelAttribute EducationDTO educationDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalEducation(educationDTO, userDTO)));

    }
    // 출장 임시 저장
    @Operation(summary = "출장 기안 임시 저장")
    @PostMapping("/saveApprovalBusinessTrip")
    public ResponseEntity<ResponseDTO> saveApprovalBusinessTrip(@ModelAttribute BusinessTripDTO businessTripDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalBusinessTrip(businessTripDTO, userDTO)));

    }
    // 일반 임시 저장
    @Operation(summary = "일반 기안 임시 저장")
    @PostMapping("/saveApprovalGeneral")
    public ResponseEntity<ResponseDTO> saveApprovalGeneral(@ModelAttribute GeneralDraftDTO generalDraftDTO, @AuthenticationPrincipal UserDTO userDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.temporarySaveApprovalGeneralDraft(generalDraftDTO, userDTO)));

    }

    // 임시 기안 문서 조회
    @Operation(summary = "임시 저장한 문서 조회")
    @GetMapping("/tempSaveDocument")
    public ResponseEntity<ResponseDTO> selectTempSaveDocument(@AuthenticationPrincipal UserDTO userDTO, @RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[ProductController] selectProductListWithPaging Start ============ ");
        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();




        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        Page<DocumentDTO> documentList = approvalService.findTempSaveDocument(userDTO, cri);
        pagingResponseDTO.setData(documentList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */




        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));

        log.info("[ProductController] selectProductListWithPaging End ============ ");




        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", pagingResponseDTO));
    }

    // 임시 기안 문서 조회 후 기안 추가
    @Operation(summary = "임시 기안한 문서 조회 후 문서 기안")
    @PutMapping("/insertBySelectTempDocument/{documentCode}")
    public ResponseEntity<ResponseDTO> insertDayOffApplyBySelectTempDocument(@PathVariable int documentCode, @ModelAttribute DayOffApplyDTO dayOffApplyDTO ,BusinessTripDTO businessTripDTO ,EducationDTO educationDTO ,GeneralDraftDTO generalDraftDTO , MultipartFile file, @AuthenticationPrincipal UserDTO userDTO) throws IOException {
        System.out.println("dayOffApplyDTO======================================= " + dayOffApplyDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.insertBySelectTempDocument(documentCode, dayOffApplyDTO ,businessTripDTO ,educationDTO ,generalDraftDTO ,file ,userDTO)));
    }

    @Operation(summary = "임시 기안한 문서 상세 조회 ")
    @GetMapping("/selectTempDocumentDetail/{documentCode}")
    public ResponseEntity<ResponseDTO> selectTempDocumentDetail(@PathVariable int documentCode){
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectTempDocumentDetail(documentCode)));
    }

    // 임시 기안 삭제
    @Operation(summary = "임시 기안 삭제")
    @DeleteMapping("/deleteTempApproval")
    public ResponseEntity<ResponseDTO> deleteTempApproval(@RequestParam("documentCode") int[] documentCode) {

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "임시 기안 삭제 성공", approvalService.deleteTempApproval(documentCode)));
    }

    // 대리 결재 위임
    @Operation(summary = "대리 결재 위임")
    @PostMapping("/insertProxy")
    public ResponseEntity<ResponseDTO> insertProxy(@RequestBody ProxyApprovalDTO proxyApprovalDTO, @AuthenticationPrincipal UserDTO userDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "대리결재 위임 성공", approvalService.insertProxy(proxyApprovalDTO, userDTO)));
    }

    //대리 결재 취소
    @Operation(summary = "대리 결재 위임 취소")
    @PutMapping("/updateProxy/{proxyCode}")
    public ResponseEntity<ResponseDTO> updateProxy(@PathVariable int proxyCode) {

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "대리결재 위임 취소", approvalService.updateProxy(proxyCode)));
    }

    // 기안 문서에 댓글 달기
    @Operation(summary = "결재,반려 등 결재선에 지정된 결재자 댓글")
    @PostMapping("/approvalComment")
    public ResponseEntity<ResponseDTO> approvalComment(@RequestBody ApprovalCommentDTO approvalCommentDTO, @AuthenticationPrincipal UserDTO userDTO){

        System.out.println("approvalCommentDTO = " + approvalCommentDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "문서 댓글 작성 성공", approvalService.insertApprovalComment(approvalCommentDTO, userDTO)));

    }

    // 댓글 조회
    @Operation(summary = "댓글조회")
    @GetMapping("/approvalComment1/{documentCode}")
    public ResponseEntity<ResponseDTO> selectComment(@PathVariable int documentCode)  {
        System.out.println("documentCode = " + documentCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "댓글 조회 성공", approvalService.selectComment(documentCode)));

    }

    // 재기안
    @Operation(summary = "반려, 완료 등 문서 재기안")
    @PostMapping("/reDraft/{documentCode}")
    public ResponseEntity<ResponseDTO> reDraft(@PathVariable int documentCode, @ModelAttribute DayOffApplyDTO dayOffApplyDTO ,BusinessTripDTO businessTripDTO ,EducationDTO educationDTO ,GeneralDraftDTO generalDraftDTO , MultipartFile file, @AuthenticationPrincipal UserDTO userDTO) throws IOException {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "재기안 성공", approvalService.reDraft(documentCode, dayOffApplyDTO ,businessTripDTO ,educationDTO ,generalDraftDTO ,file ,userDTO)));
    }

    // 문서 공유
    @Operation(summary = "완료된 문서 공유")
    @PostMapping("/insertSharedDocument/{documentCode}/{userCode}")
    public ResponseEntity<ResponseDTO> insertSharedDocument(@PathVariable int documentCode, @PathVariable int userCode)  {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "문서 공유 성공", approvalService.insertSharedDocument(documentCode,userCode)));

    }
    // 받은 공유 문서 조회
    @Operation(summary = "공유 받은 문서 조회")
    @GetMapping("/selectSharedDocument")
    public ResponseEntity<ResponseDTO> selectSharedDocument(@AuthenticationPrincipal UserDTO userDTO, @RequestParam(name = "offset", defaultValue = "1") String offset)  {

        log.info("[ProductController] selectProductListWithPaging Start ============ ");
        log.info("[ProductController] selectProductListWithPaging offset : {} ", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        /* 1. offset의 번호에 맞는 페이지에 뿌려줄 Product들 */
        Page<DocumentDTO> documentList = approvalService.selectSharedDocument(userDTO, cri);
        pagingResponseDTO.setData(documentList);
        /* 2. PageDTO : 화면에서 페이징 처리에 필요한 정보들 */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) documentList.getTotalElements()));
        System.out.println("pagingResponseDTO = " + pagingResponseDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공유 문서 조회 성공", pagingResponseDTO));

    }

    // 유저코드로 로그인 유저 조회
    @Operation(summary = "완료된 문서 공유")
    @GetMapping("/findUserDetail/{userCode}")
    public ResponseEntity<ResponseDTO> findByUserCode(@RequestParam int userCode)  {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "유저 조회 성공", approvalService.findByUserCode(userCode)));

    }


    // 대리결재 조회
    @Operation(summary = "대리결재 조회")
    @GetMapping("/proxy")
    public ResponseEntity<ResponseDTO> proxy(@AuthenticationPrincipal UserDTO userDTO)  {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "대리결재 조회 성공", approvalService.selectProxy(userDTO)));

    }


    // 대리 결재할 시 상태값 Y인 대리자 확인용도 조회
    @Operation(summary = "대리결재시 조건용 조회")
    @GetMapping("/proxy2")
    public ResponseEntity<ResponseDTO> proxy2(@AuthenticationPrincipal UserDTO userDTO)  {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "대리결재 조회 성공", approvalService.selectProxy2(userDTO)));

    }










    //기안추가
    @Operation(summary = "휴가 기안 등록")
    @PostMapping("/dayOffApply")
    public ResponseEntity<ResponseDTO> insertDayOffApply(@ModelAttribute DayOffApplyDTO dayOffApplyDTO, MultipartFile file,@AuthenticationPrincipal UserDTO principal, Document document ) throws IOException {
        System.out.println("principal=============================================================== = " + principal);
        System.out.println("file==============="+file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 임시 저장 성공", approvalService.insertDayOffApply(dayOffApplyDTO, file, principal, document)));
    }
    @Operation(summary = "출장 기안 등록")
    @PostMapping("/businessTrip")
    public ResponseEntity<ResponseDTO> insertBusinessTrip(@ModelAttribute BusinessTripDTO businessTripDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal, Document document) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertBusinessTrip(businessTripDTO, file, principal, document)));
    }
    @Operation(summary = "교육 기안 등록")
    @PostMapping("/education")
    public ResponseEntity<ResponseDTO> insertEducation(@ModelAttribute EducationDTO educationDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal, Document document) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertEducation(educationDTO, file, principal, document)));
    }
    @Operation(summary = "일반 기안 등록")
    @PostMapping("/generalDraft")
    public ResponseEntity<ResponseDTO> insertgeneralDraft(@ModelAttribute GeneralDraftDTO generalDraftDTO, MultipartFile file, @AuthenticationPrincipal UserDTO principal, Document document) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "기안 등록 성공", approvalService.insertgeneralDraft(generalDraftDTO, file, principal, document)));
    }



    @Operation(summary = "유저정보")
    @GetMapping("/user")
    public ResponseEntity<ResponseDTO> insertgeneralDraft(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공유 문서 조회 성공", approvalService.findUserDetail(userDTO)));

    }



    // 참조 받은 문서 진행중 조회
    @Operation(summary = "참조 받은 진행중문서 조회")
    @GetMapping("/rfdocument")
    public ResponseEntity<ResponseDTO> selectRfDocument(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "참조 받은 문서 조회 성공", approvalService.selectRfDocument(userDTO)));

    }

    // 참조 받은 문서 완료 조회
    @Operation(summary = "참조 받은 완료 문서 조회")
    @GetMapping("/rfdocumentcomplete")
    public ResponseEntity<ResponseDTO> selectRfDocumentComplete(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "참조 받은 문서 조회 성공", approvalService.selectRfDocumentComplete(userDTO)));

    }

    // 결재한 문서 중에 완료 조회
    @Operation(summary = "결재한 문서 중에 문서 조회")
    @GetMapping("/linedocumentcomplete")
    public ResponseEntity<ResponseDTO> selectLineDocumentComplete(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "결재한 문서 중에 완료된 문서 조회 성공", approvalService.selectLineDocumentComplete(userDTO)));

    }

    // 결재한 문서 중에 진행중 조회
    @Operation(summary = "결재한 문서 중에 진행중 조회")
    @GetMapping("/linedocument")
    public ResponseEntity<ResponseDTO> selectLineDocument(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "결재한 문서 중에 완료된 문서 조회 성공", approvalService.selectLineDocument(userDTO)));

    }

    // 내문서함 전체 문서 갯수
    @Operation(summary = "내문서함 전체 문서 갯수")
    @GetMapping("/totaldocument")
    public ResponseEntity<ResponseDTO> totaldocument(@AuthenticationPrincipal UserDTO userDTO ) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "내문서함 전체 문서 갯수 조회 성공", approvalService.totaldocument(userDTO)));

    }
    @Operation(summary = "진행중인 전체 문서 갯수")
    @GetMapping("/totaldocumentproceeding")
    public ResponseEntity<ResponseDTO> totaldocumentproceeding(@AuthenticationPrincipal UserDTO userDTO ) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "진행중인 문서함 전체 문서 갯수 조회 성공", approvalService.totaldocumentproceeding(userDTO)));

    }

    @Operation(summary = "첨부 파일 조회")
    @GetMapping("/selectfile/{documentCode}")
    public ResponseEntity<ResponseDTO> selectfile(@PathVariable int documentCode ) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "첨부파일 조회 성공", approvalService.selectfile(documentCode)));

    }






}

package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.*;
import com.energizor.restapi.approval.repository.*;
import com.energizor.restapi.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApprovalService {

    private final BusinessTripRepository businessTripRepository;
    private final DayOffApplyRepository dayOffApplyRepository;
    private final EducationRepository educationRepository;
    private final GeneralDraftRepository generalDraftRepository;
    private final ReferenceRepository referenceRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final ApprovalCommentRepository approvalCommentRepository;
    private final ApprovalFileRepository approvalFileRepository;
    private final ProxyApprovalRepository proxyApprovalRepository;
    private final SharedDocumentRepository sharedDocumentRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(BusinessTripRepository businessTripRepository, DayOffApplyRepository dayOffApplyRepository, EducationRepository educationRepository, GeneralDraftRepository generalDraftRepository, ReferenceRepository referenceRepository, DocumentRepository documentRepository, ApprovalLineRepository approvalLineRepository, ApprovalCommentRepository approvalCommentRepository, ApprovalFileRepository approvalFileRepository, ProxyApprovalRepository proxyApprovalRepository, SharedDocumentRepository sharedDocumentRepository, ModelMapper modelMapper) {

        this.businessTripRepository = businessTripRepository;
        this.dayOffApplyRepository = dayOffApplyRepository;
        this.educationRepository = educationRepository;
        this.generalDraftRepository = generalDraftRepository;
        this.referenceRepository = referenceRepository;
        this.documentRepository = documentRepository;
        this.approvalLineRepository = approvalLineRepository;
        this.approvalCommentRepository = approvalCommentRepository;
        this.approvalFileRepository = approvalFileRepository;
        this.proxyApprovalRepository = proxyApprovalRepository;
        this.sharedDocumentRepository = sharedDocumentRepository;
        this.modelMapper = modelMapper;
    }


//    public List<DocumentDTO> selectApprovalList() {
//        log.info("approvalservice 시작  selectApprovalList================================");
//
//        List<Document> selectApprovalList = approvalRepository.findByDocumentCode(1);
//
//        List<DocumentDTO> documentDTOList = selectApprovalList.stream()
//                .map(document -> modelMapper.map(document, DocumentDTO.class)).collect(Collectors.toList());
//
//
//        for(int i = 0; i < documentDTOList.size(); i++){
//            documentDTOList.get(i);
//        }
//        log.info("[ProductService] selectProductList End ===================");
//        return documentDTOList;
//    }
    // 기얀양식 전체조회

    public List<GeneralDraftDTO> selectGeneralDraft() {
        List<GeneralDraft> generalDrafts = generalDraftRepository.findAll();
        return generalDrafts.stream()
                .map(generalDraft -> modelMapper.map(generalDraft, GeneralDraftDTO.class))
                .collect(Collectors.toList());

    }

    public List<EducationDTO> selectEducation() {
        List<Education> educations  = educationRepository.findAll();
        return educations.stream()
                .map(education -> modelMapper.map(education, EducationDTO.class))
                .collect(Collectors.toList());

    }

    public List<BusinessTripDTO> selectBusinessTrip() {
        List<BusinessTrip> businessTrips = businessTripRepository.findAll();
        return businessTrips.stream()
                .map(businessTrip -> modelMapper.map(businessTrip, BusinessTripDTO.class))
                .collect(Collectors.toList());

    }


    public List<DayOffApplyDTO>  selectDayOffApply() {
        List<DayOffApply> dayOffApplies = dayOffApplyRepository.findAll();
        return dayOffApplies.stream()
                .map(dayOffApply -> modelMapper.map(dayOffApply, DayOffApplyDTO.class))
                .collect(Collectors.toList());

    }

    // 참조자, 결재자, 공유 문서 전체 조회

    public List<ReferenceDTO> selectReference() {
        List<Reference> references = referenceRepository.findAll();
        return references.stream()
                .map(reference -> modelMapper.map(reference, ReferenceDTO.class))
                .collect(Collectors.toList());
    }

    public List<SharedDocumentsDTO> selectSharedDocument() {
        List<SharedDocument> sharedDocuments = sharedDocumentRepository.findAll();
        return sharedDocuments.stream()
                .map(sharedDocument -> modelMapper.map(sharedDocument, SharedDocumentsDTO.class))
                .collect(Collectors.toList());
    }

    public List<ApprovalLineDTO> selectApprovalLine() {
        List<ApprovalLine> approvalLines = approvalLineRepository.findAll();
        return approvalLines.stream()
                .map(approvalLine -> modelMapper.map(approvalLine, ApprovalLineDTO.class))
                .collect(Collectors.toList());
    }

    public List<DocumentDTO> selectDocument() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    // 기타(댓글, 결재위임), 첨부파일 전체조회

    public List<ApprovalCommentDTO> selectApprovalComment() {
        List<ApprovalComment> approvalComments = approvalCommentRepository.findAll();
        return approvalComments.stream()
                .map(approvalComment -> modelMapper.map(approvalComment, ApprovalCommentDTO.class))
                .collect(Collectors.toList());
    }

    public List<ApprovalFileDTO> selectApprovalFile() {
        List<ApprovalFile> approvalFiles = approvalFileRepository.findAll();
        return approvalFiles.stream()
                .map(approvalFile -> modelMapper.map(approvalFile, ApprovalFileDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProxyApprovalDTO> selectProxyApproval() {
        List<ProxyApproval> proxyApprovals = proxyApprovalRepository.findAll();
        return proxyApprovals.stream()
                .map(proxyApproval -> modelMapper.map(proxyApproval, ProxyApprovalDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String insertDayOffApply(DayOffApplyDTO dayOffApplyDTO) {

        // 기안 -> 기안번호 조회
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDocumentTitle(dayOffApplyDTO.getOffApplyTitle());
        documentDTO.setDraftDay(dayOffApplyDTO.getOffApplyDate());
        
        Document document = modelMapper.map(documentDTO, Document.class);
        Document result = documentRepository.save(document);


        // 기안코드를 휴가신청

        // 휴가신청서

        dayOffApplyDTO.setOffApplyCode(result.getDocumentCode());

        DayOffApply dayOffApply = modelMapper.map(dayOffApplyDTO, DayOffApply.class);
        DayOffApply result2 = dayOffApplyRepository.save(dayOffApply);
        return "";
    }
}

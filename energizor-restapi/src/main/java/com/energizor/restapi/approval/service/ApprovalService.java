package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.*;
import com.energizor.restapi.approval.repository.*;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final DayOffRepository dayOffRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public ApprovalService(BusinessTripRepository businessTripRepository, DayOffApplyRepository dayOffApplyRepository, EducationRepository educationRepository, GeneralDraftRepository generalDraftRepository, ReferenceRepository referenceRepository, DocumentRepository documentRepository, ApprovalLineRepository approvalLineRepository, ApprovalCommentRepository approvalCommentRepository, ApprovalFileRepository approvalFileRepository, ProxyApprovalRepository proxyApprovalRepository, SharedDocumentRepository sharedDocumentRepository, DayOffRepository dayOffRepository, ModelMapper modelMapper, UserRepository userRepository) {

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
        this.dayOffRepository = dayOffRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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
    public String insertDayOffApply(DayOffApplyDTO dayOffApplyDTO, UserDTO principal) {
        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);

        User user1 = userRepository.findByUserCode(principal.getUserCode());
        User user = modelMapper.map(user1, User.class);

        LocalDate now =LocalDate.now();
        dayOffApplyDTO.setOffApplyDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(dayOffApplyDTO.getOffApplyTitle())
                        .userDTO(user)
                        .draftDay(dayOffApplyDTO.getOffApplyDate())
                .form("휴가신청서").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);
//        DocumentDTO documentDTO1 = modelMapper.map(result, DocumentDTO.class);
//        System.out.println("documentDTO1 : " + documentDTO1);

        // 기안코드를 휴가신청


        // 현재 년도 계산


        // 현재 년도와 사용자 정보를 기반으로 offCode 조회

        // 휴가신청서
        DayOffApply dayOffApply = new DayOffApply();
        dayOffApply.document(result);
        dayOffApply.user(user);
        dayOffApply.dayoff(user.getDayoff());
        dayOffApply.offApplyTitle(dayOffApplyDTO.getOffApplyTitle());
        dayOffApply.offApplyDate(dayOffApplyDTO.getOffApplyDate());
        dayOffApply.offStart(dayOffApplyDTO.getOffStart());
        dayOffApply.offEnd(dayOffApplyDTO.getOffEnd());
        dayOffApply.offDay(dayOffApplyDTO.getOffDay());
        dayOffApply.offReason(dayOffApplyDTO.getOffReason());
        dayOffApply.offState(dayOffApplyDTO.getOffState());

        System.out.println("dayOffApply : " + dayOffApply);

        DayOffApply result2 = dayOffApplyRepository.save(dayOffApply);
        return "휴가신청서 기안 성공";
    }
    @Transactional
    public String insertBusinessTrip(BusinessTripDTO businessTripDTO, UserDTO principal) {
        // 기안 -> 기안번호 조회

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("businessTripDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + businessTripDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        businessTripDTO.setBtDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(businessTripDTO.getBtTitle())
                .userDTO(user)
                .draftDay(businessTripDTO.getBtDate())
                .form("출장신청서").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        // 기안코드를 휴가신청

        // 휴가신청서
        BusinessTrip businessTrip = new BusinessTrip();


        businessTrip.documentDTO(result);
        businessTrip.user(user);
        businessTrip.btDate(businessTripDTO.getBtDate());
        businessTrip.btPhone(businessTripDTO.getBtPhone());
        businessTrip.btStart(businessTripDTO.getBtStart());
        businessTrip.btFinish(businessTripDTO.getBtFinish());
        businessTrip.btPlace(businessTripDTO.getBtPlace());
        businessTrip.btContent(businessTripDTO.getBtContent());
        businessTrip.btTitle(businessTripDTO.getBtTitle());

        BusinessTrip result2 = businessTripRepository.save(businessTrip);
        return "등록 성공";
    }

    public String insertEducation(EducationDTO educationDTO, UserDTO principal) {
        // 기안 -> 기안번호 조회

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("educationDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + educationDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        educationDTO.setEduDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(educationDTO.getEduTitle())
                .userDTO(user)
                .draftDay(educationDTO.getEduDate())
                .form("교육신청서").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        // 기안코드를 휴가신청

        // 휴가신청서
        Education education = new Education();


        education.document(result);
        education.user(user);
//        education.btDate(businessTripDTO.getBtDate());
//        businessTrip.btPhone(businessTripDTO.getBtPhone());
//        businessTrip.btStart(businessTripDTO.getBtStart());
//        businessTrip.btFinish(businessTripDTO.getBtFinish());
//        businessTrip.btPlace(businessTripDTO.getBtPlace());
//        businessTrip.btContent(businessTripDTO.getBtContent());
//        businessTrip.btTitle(businessTripDTO.getBtTitle());
//
//        BusinessTrip result2 = businessTripRepository.save(businessTrip);
        return "등록 성공";
    }
}

package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.*;
import com.energizor.restapi.approval.repository.*;
import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import com.energizor.restapi.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.SourceVersion;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    private final UserAndTeamRepository userAndTeamRepository;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public ApprovalService(BusinessTripRepository businessTripRepository, DayOffApplyRepository dayOffApplyRepository, EducationRepository educationRepository, GeneralDraftRepository generalDraftRepository, ReferenceRepository referenceRepository, DocumentRepository documentRepository, ApprovalLineRepository approvalLineRepository, ApprovalCommentRepository approvalCommentRepository, ApprovalFileRepository approvalFileRepository, ProxyApprovalRepository proxyApprovalRepository, SharedDocumentRepository sharedDocumentRepository, DayOffRepository dayOffRepository, ModelMapper modelMapper, UserRepository userRepository, UserAndTeamRepository userAndTeamRepository) {

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
        this.userAndTeamRepository = userAndTeamRepository;
    }


    @Transactional
    public String insertDayOffApply(DayOffApplyDTO dayOffApplyDTO, MultipartFile file, UserDTO principal, Document document) throws IOException {
        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("document==================================== " + document);
        System.out.println("dayOffApplyDTO============================================= " + dayOffApplyDTO);

        User user1 = userRepository.findByUserCode(principal.getUserCode());
        User user = modelMapper.map(user1, User.class);

        LocalDate now = LocalDate.now();
        dayOffApplyDTO.setOffApplyDate(now);
        // 기안 -> 기안번호 조회

        document.documentTitle(dayOffApplyDTO.getOffApplyTitle())
                .userDTO(user)
                .draftDay(dayOffApplyDTO.getOffApplyDate())
                .form("휴가신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);
        System.out.println("dayOffApplyDTO============================================= " + dayOffApplyDTO);

        Document result = documentRepository.save(document);
        System.out.println("file = " + file);


        if(file != null){
            // 파일의 원본 이름을 가져옵니다.
            String originalFileName = file.getOriginalFilename();

            // FileUploadUtils를 사용하여 파일을 저장하고, 저장된 파일 이름을 반환 받습니다.
            String storedFileName = FileUploadUtils.saveFile(IMAGE_DIR, originalFileName, file);


            // 데이터베이스에 파일 정보를 저장합니다.
            ApprovalFile approvalFile = new ApprovalFile();
            approvalFile.apFileNameOrigin(originalFileName);
            approvalFile.apFileNameChange(storedFileName);
            approvalFile.apFileDate(new Date());
            approvalFile.apFileStatus("N");
            approvalFile.document(result);

            // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
            approvalFileRepository.save(approvalFile);
        }

        if(!dayOffApplyDTO.getRfUser().equals("")){
            // 참조, 결재선 지정
            int[] rfUser = changeUser(dayOffApplyDTO.getRfUser());
            for (int i = 0; i < rfUser.length; i++) {
                if (rfUser[i] > 0) {


                    User userCode1 = userRepository.findByUserCode(rfUser[i]);
                    User userCode = modelMapper.map(userCode1, User.class);


                    System.out.println("userCode================================ " + userCode);


                    Reference reference = new Reference();
                    reference.document(result);
                    reference.user(userCode);
                    reference.referenceStatus("N");


                    referenceRepository.save(reference);

                }

            }
        }

        int[] lineUser = changeUser(dayOffApplyDTO.getLineUser());
        for (int i = 0; i < lineUser.length; i++) {


            if (lineUser[i] > 0) {


                User userCode1 = userRepository.findByUserCode(lineUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i + 1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);

                approvalLineRepository.save(approvalLine);

            }

        }


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
        dayOffApply.offState("대기");

        System.out.println("dayOffApply : " + dayOffApply);

        DayOffApply result2 = dayOffApplyRepository.save(dayOffApply);
        return "휴가신청서 기안 성공";
    }

    @Transactional
    public String insertBusinessTrip(BusinessTripDTO businessTripDTO, MultipartFile file, UserDTO principal, Document document) throws IOException {
        // 기안 -> 기안번호 조회

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("businessTripDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + businessTripDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        businessTripDTO.setBtDate(now);
        // 기안 -> 기안번호 조회
        document.documentTitle(businessTripDTO.getBtTitle())
                .userDTO(user)
                .draftDay(businessTripDTO.getBtDate())
                .form("출장신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        if(file != null){
            // 파일의 원본 이름을 가져옵니다.
            String originalFileName = file.getOriginalFilename();

            // FileUploadUtils를 사용하여 파일을 저장하고, 저장된 파일 이름을 반환 받습니다.
            String storedFileName = FileUploadUtils.saveFile(IMAGE_DIR, originalFileName, file);


            // 데이터베이스에 파일 정보를 저장합니다.
            ApprovalFile approvalFile = new ApprovalFile();
            approvalFile.apFileNameOrigin(originalFileName);
            approvalFile.apFileNameChange(storedFileName);
            approvalFile.apFileDate(new Date());
            approvalFile.apFileStatus("N");
            approvalFile.document(result);

            // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
            approvalFileRepository.save(approvalFile);
        }

        // 참조, 결재선 지정

        if(!businessTripDTO.getRfUser().equals("")){
            int[] rfUser = changeUser(businessTripDTO.getRfUser());


            for (int i = 0; i < rfUser.length; i++) {
                if (rfUser[i] > 0) {


                    User userCode1 = userRepository.findByUserCode(rfUser[i]);
                    User userCode = modelMapper.map(userCode1, User.class);


                    System.out.println("userCode================================ " + userCode);


                    Reference reference = new Reference();
                    reference.document(result);
                    reference.user(userCode);
                    reference.referenceStatus("N");


                    referenceRepository.save(reference);

                }

            }
        }

        int[] lineUser = changeUser(businessTripDTO.getLineUser());


        for (int i = 0; i < lineUser.length; i++) {

            if (lineUser[i] > 0) {


                User userCode1 = userRepository.findByUserCode(lineUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i + 1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);

                approvalLineRepository.save(approvalLine);

            }

        }

        // 출장신청서
        BusinessTrip businessTrip = new BusinessTrip();


        businessTrip.document(result);
        businessTrip.user(user);
        businessTrip.btDate(businessTripDTO.getBtDate());
        businessTrip.btPhone(businessTripDTO.getBtPhone());
        businessTrip.btStart(businessTripDTO.getBtStart());
        businessTrip.btFinish(businessTripDTO.getBtFinish());
        businessTrip.btPlace(businessTripDTO.getBtPlace());
        businessTrip.btContent(businessTripDTO.getBtContent());
        businessTrip.btTitle(businessTripDTO.getBtTitle());

        businessTripRepository.save(businessTrip);
        return "등록 성공";
    }

    public String insertEducation(EducationDTO educationDTO, MultipartFile file, UserDTO principal, Document document) throws IOException {
        // 기안 -> 기안번호 조회

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("educationDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + educationDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        educationDTO.setEduDate(now);
        // 기안 -> 기안번호 조회
        document.documentTitle(educationDTO.getEduTitle())
                .userDTO(user)
                .draftDay(educationDTO.getEduDate())
                .form("교육신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        if(file != null){
            // 파일의 원본 이름을 가져옵니다.
            String originalFileName = file.getOriginalFilename();

            // FileUploadUtils를 사용하여 파일을 저장하고, 저장된 파일 이름을 반환 받습니다.
            String storedFileName = FileUploadUtils.saveFile(IMAGE_DIR, originalFileName, file);


            // 데이터베이스에 파일 정보를 저장합니다.
            ApprovalFile approvalFile = new ApprovalFile();
            approvalFile.apFileNameOrigin(originalFileName);
            approvalFile.apFileNameChange(storedFileName);
            approvalFile.apFileDate(new Date());
            approvalFile.apFileStatus("N");
            approvalFile.document(result);

            // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
            approvalFileRepository.save(approvalFile);
        }


        // 참조, 결재선 지정
        if(!educationDTO.getRfUser().equals("")){
            int[] rfUser = changeUser(educationDTO.getRfUser());
            for (int i = 0; i < rfUser.length; i++) {
                if (rfUser[i] > 0) {


                    User userCode1 = userRepository.findByUserCode(rfUser[i]);
                    User userCode = modelMapper.map(userCode1, User.class);


                    System.out.println("userCode================================ " + userCode);


                    Reference reference = new Reference();
                    reference.document(result);
                    reference.user(userCode);
                    reference.referenceStatus("N");

                    referenceRepository.save(reference);

                }

            }
        }

        int[] lUser = changeUser(educationDTO.getLineUser());
        for (int i = 0; i < lUser.length; i++) {


            if (lUser[i] > 0) {


                User userCode1 = userRepository.findByUserCode(lUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i + 1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);

                approvalLineRepository.save(approvalLine);

            }

        }

        // 교육신청서
        Education education = new Education();


        education.document(result);
        education.user(user);
        education.eduTitle(educationDTO.getEduTitle());
        education.eduDate(educationDTO.getEduDate());
        education.eduName(educationDTO.getEduName());
        education.eduStart(educationDTO.getEduStart());
        education.eduFinish(educationDTO.getEduFinish());
        education.eduInstitution(educationDTO.getEduInstitution());
        education.eduPrice(educationDTO.getEduPrice());
        education.eduContent(educationDTO.getEduContent());

        educationRepository.save(education);
        return "등록 성공";
    }
    @Transactional
    public String insertgeneralDraft(GeneralDraftDTO generalDraftDTO, MultipartFile file, UserDTO principal, Document document) throws IOException {

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("generalDraftDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + generalDraftDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        generalDraftDTO.setGdDate(now);
        // 기안 -> 기안번호 조회
        document.documentTitle(generalDraftDTO.getGdTitle())
                .userDTO(user)
                .draftDay(generalDraftDTO.getGdDate())
                .form("기안신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        System.out.println("file = " + file);
        if (file != null){


        // 파일의 원본 이름을 가져옵니다.
        String originalFileName = file.getOriginalFilename();

        // FileUploadUtils를 사용하여 파일을 저장하고, 저장된 파일 이름을 반환 받습니다.
        String storedFileName = FileUploadUtils.saveFile(IMAGE_DIR, originalFileName, file);


        // 데이터베이스에 파일 정보를 저장합니다.
        ApprovalFile approvalFile = new ApprovalFile();
        approvalFile.apFileNameOrigin(originalFileName);
        approvalFile.apFileNameChange(storedFileName);
        approvalFile.apFileDate(new Date());
        approvalFile.apFileStatus("N");
        approvalFile.document(result);

        // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
        approvalFileRepository.save(approvalFile);
        }


            // 참조, 결재선 지정
            if(!generalDraftDTO.getRfUser().equals("")){
            int[] rfUser = changeUser(generalDraftDTO.getRfUser());
            for (int i = 0; i < rfUser.length; i++) {
                if (rfUser[i] > 0) {


                    User userCode1 = userRepository.findByUserCode(rfUser[i]);
                    User userCode = modelMapper.map(userCode1, User.class);


                    System.out.println("userCode================================ " + userCode);


                    Reference reference = new Reference();
                    reference.document(result);
                    reference.user(userCode);
                    reference.referenceStatus("N");



                        referenceRepository.save(reference);
                    }

                }

            }


        int[] lUser = changeUser(generalDraftDTO.getLineUser());
        for (int i = 0; i < lUser.length; i++) {


            if (lUser[i] > 0) {
                User userCode1 = userRepository.findByUserCode(lUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);

                System.out.println("userCode================================ " + userCode);

                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i + 1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);

                approvalLineRepository.save(approvalLine);

            }

        }

        // 일반기안
        GeneralDraft generalDraft = new GeneralDraft();
        generalDraft.gdDate(generalDraftDTO.getGdDate());
        generalDraft.gdTitle(generalDraftDTO.getGdTitle());
        generalDraft.gdContent(generalDraftDTO.getGdContent());
        generalDraft.document(result);
        generalDraft.user(user);

        generalDraftRepository.save(generalDraft);
        return "등록 성공";
    }

    // 들고온 유저코드 분할작업
    public int[] changeUser(String user) {
        String[] rfUserArr = user.split(",");
        int[] rfUserIntArr = Arrays.stream(rfUserArr)
                .mapToInt(Integer::parseInt)
                .toArray();

        return rfUserIntArr;
    }

    // 기안한 문서 조회
    public Page<DocumentDTO> findDocumentsByUserCode(UserDTO userDTO, Criteria cri) {
        // 로그인한 사용자의 정보 가져오기
        System.out.println("userDTO1111111111111111111111111 = " + userDTO);
        User user = modelMapper.map(userDTO, User.class);
        System.out.println("user1111111111111111111111111111 = " + user);
        //페이징
        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("documentCode").descending());

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Page<Document> documentList = documentRepository.findByUserDTOAndTempSaveStatus(user, "N", paging);

        // 기안 문서 댓글 조회
        List<Document> document1 = new ArrayList<>();
        for (Document document2 : documentList){
            Document document3 = documentRepository.findByDocumentCode(document2.getDocumentCode());
            List<ApprovalComment> approvalComment = approvalCommentRepository.findByDocument(document2);
            document3.approvalComment(approvalComment);
            document1.add(document3);
        }




        return documentList.map(document -> modelMapper.map(document, DocumentDTO.class));
    }

    // 결재 대기 문서 조회
    public List<DocumentDTO> inBoxDocumentByUserCode(UserDTO userDTO) {

        User changeUser = modelMapper.map(userDTO, User.class);
        System.out.println("changeUser===================================== " + changeUser);

        // 대리결재자 조회
        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUserUserCode(changeUser.getUserCode());

        System.out.println("proxyApproval========================= = " + proxyApproval);

        if (proxyApproval != null && proxyApproval.getProxyStatus().equals("Y")) {
            // 문서 코드 목록 가져오기
            List<Integer> inboxDocumentList = documentRepository.inboxDocumentByUserDTO(proxyApproval.getOriginUser().getUserCode());

            System.out.println("inboxDocumentList ============================= " + inboxDocumentList);
            // 문서 코드 목록으로 Document 정보 가져오기 댓글 함꼐 조회
            List<Document> proxyDocumentList = new ArrayList<>();
            for (int documentCode : inboxDocumentList) {
                System.out.println("documentCode =========================== " + documentCode);
                Document document = documentRepository.findByDocumentCodeAndTempSaveStatus(documentCode, "N");
                System.out.println("document ============================== " + document);

                List<ApprovalComment> approvalComment = approvalCommentRepository.findByDocument(document);
                document.approvalComment(approvalComment);
                if (document != null) {
                    proxyDocumentList.add(document);
                }
            }
            System.out.println("proxyDocumentList = " + proxyDocumentList);

            return proxyDocumentList.stream()
                    .map(document -> modelMapper.map(document, DocumentDTO.class))
                    .collect(Collectors.toList());
        }


        // 문서 코드 목록 가져오기
        List<Integer> inboxDocumentList = documentRepository.inboxDocumentByUserDTO(userDTO.getUserCode());

        System.out.println("inboxDocumentList ============================ " + inboxDocumentList);
        // 문서 코드 목록으로 Document 정보 가져오기
        List<Document> documentList = new ArrayList<>();
        for (int documentCode : inboxDocumentList) {
            System.out.println("documentCode =========================== " + documentCode);
            Document document = documentRepository.findByDocumentCodeAndTempSaveStatus(documentCode, "N");
            System.out.println("document ============================== " + document);
            List<ApprovalComment> approvalComment = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalComment);
            if (document != null) {
                documentList.add(document);
            }
        }


        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());

    }

    // 결재하기
    @Transactional
    public String approvement(int documentCode, UserDTO userDTO) {




        //결재 대상 조회
        int approvalLineUser = approvalLineRepository.approvalSubjectUserCode(documentCode);
        User changeUser = modelMapper.map(userDTO, User.class);


        // 대리위임자 결재
        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUser(changeUser);
        System.out.println("proxyApproval대리결재 가능한지 조회  " + proxyApproval);

        if (proxyApproval != null && proxyApproval.getProxyStatus().equals("Y") && proxyApproval.getOriginUser().getUserCode() == approvalLineUser) {
            ApprovalLine porxyApprovalLine = approvalLineRepository.findByDocumentDocumentCodeAndUserUserCodeAndApprovalLineStatus(documentCode, proxyApproval.getOriginUser().getUserCode(), "미결");


            // 결재 상태 업데이트
            porxyApprovalLine.processingDate(LocalDateTime.now());
            porxyApprovalLine.approvalLineStatus("결재");
            
            approvalLineRepository.save(porxyApprovalLine);
            System.out.println("porxyApprovalLine 업데이트 되었는지 확인 = " + porxyApprovalLine);

            // 휴가일수 차감


            if (porxyApprovalLine.getDocument().getForm().equals("휴가신청서")) {
                List<ApprovalLine> checkStatus = approvalLineRepository.findByDocument(porxyApprovalLine.getDocument());
                int check = 0;
                for (ApprovalLine lineCheckStatus : checkStatus) {

                    if (lineCheckStatus.getApprovalLineStatus().equals("결재")) {
                        check++;
                    }

                }
                if (check == checkStatus.size()) {

                    DayOff dayOff = dayOffRepository.findByUser(porxyApprovalLine.getDocument().getUserDTO());
                    DayOffApply dayOffApply = dayOffApplyRepository.findByDocument(porxyApprovalLine.getDocument());


                    dayOff.offUsed(dayOff.getOffUsed() + dayOffApply.getOffDay());

                    dayOffRepository.save(dayOff);
                }
            }


            return "결재 성공";

        }

        // 결재대상 확인
        if (approvalLineUser != userDTO.getUserCode()) {
            return "결재 대상이 아닙니다.";
        }


        int userCode = userDTO.getUserCode();
        ApprovalLine approvalLine = approvalLineRepository.findByDocumentDocumentCodeAndUserUserCodeAndApprovalLineStatus(documentCode, userCode, "미결");

        System.out.println("approvalLine ================================== " + approvalLine);

        // 결재 상태 업데이트
        approvalLine.processingDate(LocalDateTime.now());
        approvalLine.approvalLineStatus("결재");
        approvalLineRepository.save(approvalLine);


        // 휴가일수 차감
        Document document = documentRepository.findByDocumentCodeAndForm(documentCode, "휴가신청서");

        if (document != null) {
            List<ApprovalLine> checkStatus = approvalLineRepository.findByDocument(document);
            int check = 0;
            for (ApprovalLine lineCheckStatus : checkStatus) {

                if (lineCheckStatus.getApprovalLineStatus().equals("결재")) {
                    check++;
                }

            }
            if (check == checkStatus.size()) {

                DayOff dayOff = dayOffRepository.findByUser(document.getUserDTO());
                DayOffApply dayOffApply = dayOffApplyRepository.findByDocument(document);


                dayOff.offUsed(dayOff.getOffUsed() + dayOffApply.getOffDay());

                dayOffRepository.save(dayOff);

            }
        }


        return "결재 성공";
    }

    // 반려하기
    @Transactional
    public String rejection(int documentCode, UserDTO userDTO) {

        //결재 대상 조회
        int approvalLineUser = approvalLineRepository.approvalSubjectUserCode(documentCode);

        User changeUser = modelMapper.map(userDTO, User.class);
        // 대리위임자 결재
        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUser(changeUser);
        if (proxyApproval != null && proxyApproval.getProxyStatus().equals("Y") && proxyApproval.getOriginUser().getUserCode() == approvalLineUser) {

            // 미결 상태인 모든 approvalLine 조회
            List<Integer> approvalLineList = approvalLineRepository.findLineUser(documentCode);
            System.out.println("approvalLineList =============== " + approvalLineList);

            // 각 approvalLine의 상태를 반려로 변경
            for (int approvalLineCode : approvalLineList) {
                Optional<ApprovalLine> optionalApprovalLine = approvalLineRepository.findById(approvalLineCode);
                optionalApprovalLine.ifPresent(approvalLine -> {
                    approvalLine.processingDate(LocalDateTime.now());
                    approvalLine.approvalLineStatus("반려");
                    approvalLine.getUser().userCode(proxyApproval.getChangeUser().getUserCode());
                    approvalLineRepository.save(approvalLine);
                });
            }
            return "반려 성공";

        }

        // 결재대상 확인
        if (approvalLineUser != userDTO.getUserCode()) {
            return "결재 대상이 아닙니다.";
        }


        // 미결 상태인 모든 approvalLine 조회
        List<Integer> approvalLineList = approvalLineRepository.findLineUser(documentCode);
        System.out.println("approvalLineList =============== " + approvalLineList);

        // 각 approvalLine의 상태를 반려로 변경
        for (int approvalLineCode : approvalLineList) {
            Optional<ApprovalLine> optionalApprovalLine = approvalLineRepository.findById(approvalLineCode);
            optionalApprovalLine.ifPresent(approvalLine -> {
                approvalLine.processingDate(LocalDateTime.now());
                approvalLine.approvalLineStatus("반려");
                approvalLineRepository.save(approvalLine);
            });
        }
        return "반려 성공";
    }
    // 상신 기안 회수하기
    @Transactional
    public String withdraw(int documentCode, UserDTO userDTO) {

        // 문서 정보 가져오기
        Document document = documentRepository.findById(documentCode).get();
        int userCode = document.getUserDTO().getUserCode();
        int loginCode = userDTO.getUserCode();


        // 해당 문서 userCode가 로그인한 사용자와 일치하는지 확인
        if (userCode != loginCode) {
            return "회수 대상이 아닙니다.";
        }

        // 해당 문서의 모든 approvalLine 가져오기
        List<ApprovalLine> approvalLines = approvalLineRepository.findByDocumentDocumentCode(documentCode);

        // 모든 approvalLine의 상태가 "기안" 또는 "대기"인지 확인
        for (ApprovalLine approvalLine : approvalLines) {
            String status = approvalLine.getApprovalLineStatus();
            if (!status.equals("기안") && !status.equals("미결")) {
                return "미결 문서가 아닙니다.";
            }
        }

        // approvalLine의 상태를 회수로 변경
        for (ApprovalLine approvalLine : approvalLines) {
            approvalLine.approvalLineStatus("회수");
            approvalLine.processingDate(LocalDateTime.now());
            approvalLineRepository.save(approvalLine);
        }

        return "회수 성공";
    }


    // 결재 진행중인 문서
    public List<DocumentDTO> approvalProgress(UserDTO userDTO) {

        User changeUser = modelMapper.map(userDTO, User.class);

        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUser(changeUser);
        if (proxyApproval != null && proxyApproval.getProxyStatus().equals("Y")) {
            // 해당 위임받은 결재문서 의 결재 상신 문서 리스트 조회
            List<Document> documents = documentRepository.findByUserDTOUserCode(proxyApproval.getOriginUser().getUserCode());
            // 해당 사용자의 결재 상신 문서 리스트 조회
            List<Document> mydocuments = documentRepository.findByUserDTOUserCode(changeUser.getUserCode());


            // 결재 상태 중에 미결이 존재하는 문서 리스트 조회
            // 위임받은 문서
            List<Document> documentList = new ArrayList<>();
            for (Document document1 : documents) {
                List<Integer> approvalLines = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
                // 문서 댓글 조회후 넣어주기
                List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
                document1.approvalComment(approvalCommentList);
                if (!approvalLines.isEmpty()) {
                    documentList.add(document1);

                }
            }
            // 자기 문서
            for (Document document1 : mydocuments) {
                List<Integer> approvalLines = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
                // 문서 댓글 조회후 넣어주기
                List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
                document1.approvalComment(approvalCommentList);
                if (!approvalLines.isEmpty()) {
                    documentList.add(document1);
                }
            }

            return documentList.stream()
                    .map(document -> modelMapper.map(document, DocumentDTO.class))
                    .collect(Collectors.toList());

        }

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documents = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());

        // 결재 상태 중에 미결이 존재하는 문서 리스트 조회
        List<Document> documentList = new ArrayList<>();
        for (Document document1 : documents) {
            List<Integer> approvalLines = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
            // 문서 댓글 조회후 넣어주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
            document1.approvalComment(approvalCommentList);
            if (!approvalLines.isEmpty()) {
                documentList.add(document1);
            }
        }
        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    // 결재 완료 문서 조회
    public Page<DocumentDTO> approvalComplete(UserDTO userDTO, Criteria cri) {

        // 페이징
        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("documentCode").descending());

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Page<Document> documentList = documentRepository.findDocByUserDTOUserCode(userDTO.getUserCode(), paging);

        // 상태가 결재인 문서
        List<Document> approvalComplete = new ArrayList<>();

        // 해당 사용자의 결재 상신 문서 리스트를 순회하며 쿼리를 통해 검색한 결과와 비교하여 결과 리스트에 추가
        List<Integer> completeDocCodes = approvalLineRepository.approvalComplete(userDTO.getUserCode());
        for (Document document : documentList) {
            // 문서 댓글 조회 후 넣어주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalCommentList);

            if (completeDocCodes.contains(document.getDocumentCode())) {
                approvalComplete.add(document);
            }
        }

        // 전체 문서의 수를 얻어옵니다. 이것은 실제 데이터베이스 쿼리를 통해 얻어야 할 수도 있습니다.
        long totalDocuments = documentList.getTotalElements(); // 이 예에서는 documentList에서 가져옵니다.

        // List<Document>를 Page<DocumentDTO>로 변환
        Page<DocumentDTO> documentDTOPage = new PageImpl<>(
                approvalComplete.stream()
                        .map(document -> modelMapper.map(document, DocumentDTO.class))
                        .collect(Collectors.toList()),
                paging, // 필요한 페이징 정보를 전달합니다.
                totalDocuments // 전체 문서의 수를 전달합니다.
        );

        return documentDTOPage;
    }
    // 반려 문서 조회
    public Page<DocumentDTO> rejectionInOutbox(UserDTO userDTO, Criteria cri) {

        // 페이징
        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("documentCode").descending());


        // 해당 사용자의 결재 상신 문서 리스트 조회
        Page<Document> documentList = documentRepository.findDocByUserDTOUserCode(userDTO.getUserCode(), paging);

        System.out.println("documentList = " + documentList);


        // 상태가 반려인 문서
        List<Document> rejectionDocument = new ArrayList<>();
        for (Document document : documentList) {
            List<Integer> rejectionApprovalLine = approvalLineRepository.rejectionDocument(document.getDocumentCode());
            // 댓글 조회 후 담아주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalCommentList);
            if (!rejectionApprovalLine.isEmpty()) {
                rejectionDocument.add(document);
            }
        }

        // 전체 문서의 수를 얻어옵니다. 이것은 실제 데이터베이스 쿼리를 통해 얻어야 할 수도 있습니다.
        long totalDocuments = documentList.getTotalElements(); // 이 예에서는 documentList에서 가져옵니다.

        // List<Document>를 Page<DocumentDTO>로 변환
        Page<DocumentDTO> documentDTOPage = new PageImpl<>(
                rejectionDocument.stream()
                        .map(document -> modelMapper.map(document, DocumentDTO.class))
                        .collect(Collectors.toList()),
                paging, // 필요한 페이징 정보를 전달합니다.
                totalDocuments // 전체 문서의 수를 전달합니다.
        );

        return documentDTOPage;
    }

    public Page<DocumentDTO> withdrawInOutbox(UserDTO userDTO, Criteria cri) {

        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("documentCode").descending());

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Page<Document> documentList = documentRepository.findDocByUserDTOUserCode(userDTO.getUserCode(), paging);

        System.out.println("documentList = " + documentList);

        // 상태가 결재인 문서
        List<Document> withdrawDocument = new ArrayList<>();
        for (Document document : documentList) {
            List<Integer> withdrawApprovalLine = approvalLineRepository.findWithdrawDocument(document.getDocumentCode());
            if (!withdrawApprovalLine.isEmpty()) {
                withdrawDocument.add(document);
            }
        }
        // 전체 문서의 수를 얻어옵니다. 이것은 실제 데이터베이스 쿼리를 통해 얻어야 할 수도 있습니다.
        long totalDocuments = documentList.getTotalElements(); // 이 예에서는 documentList에서 가져옵니다.

        // List<Document>를 Page<DocumentDTO>로 변환
        Page<DocumentDTO> documentDTOPage = new PageImpl<>(
                withdrawDocument.stream()
                        .map(document -> modelMapper.map(document, DocumentDTO.class))
                        .collect(Collectors.toList()),
                paging, // 필요한 페이징 정보를 전달합니다.
                totalDocuments // 전체 문서의 수를 전달합니다.
        );

        return documentDTOPage;
    }

    @Transactional
    public String temporarySaveApprovalDayOff(DayOffApplyDTO dayOffApplyDTO, UserDTO userDTO) {

        User user1 = userRepository.findByUserCode(userDTO.getUserCode());
        User user = modelMapper.map(user1, User.class);

        LocalDate now = LocalDate.now();
        dayOffApplyDTO.setOffApplyDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(dayOffApplyDTO.getOffApplyTitle())
                .userDTO(user)
                .draftDay(dayOffApplyDTO.getOffApplyDate())
                .form("휴가신청서")
                .tempSaveStatus("Y").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

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
        dayOffApply.offState("대기");

        System.out.println("dayOffApply : " + dayOffApply);

        dayOffApplyRepository.save(dayOffApply);
        return "휴가 신청서 기안 임시 저장 성공";
    }

    public String temporarySaveApprovalEducation(EducationDTO educationDTO, UserDTO userDTO) {
        // 기안 -> 기안번호 조회

        System.out.println("userDTO@@@@@@@@@@@@@@@@@@@@ = " + userDTO);
        System.out.println("educationDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + educationDTO);


        User user = modelMapper.map(userDTO, User.class);

        LocalDate now = LocalDate.now();
        educationDTO.setEduDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(educationDTO.getEduTitle())
                .userDTO(user)
                .draftDay(educationDTO.getEduDate())
                .form("교육신청서")
                .tempSaveStatus("Y").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);


        // 교육신청서
        Education education = new Education();


        education.document(result);
        education.user(user);
        education.eduTitle(educationDTO.getEduTitle());
        education.eduDate(educationDTO.getEduDate());
        education.eduName(educationDTO.getEduName());
        education.eduStart(educationDTO.getEduStart());
        education.eduFinish(educationDTO.getEduFinish());
        education.eduInstitution(educationDTO.getEduInstitution());
        education.eduPrice(educationDTO.getEduPrice());
        education.eduContent(educationDTO.getEduContent());

        educationRepository.save(education);
        return "등록 성공";
    }

    public String temporarySaveApprovalBusinessTrip(BusinessTripDTO businessTripDTO, UserDTO userDTO) {
        System.out.println("userDTO@@@@@@@@@@@@@@@@@@@@ = " + userDTO);
        System.out.println("businessTripDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + businessTripDTO);


        User user = modelMapper.map(userDTO, User.class);

        LocalDate now = LocalDate.now();
        businessTripDTO.setBtDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(businessTripDTO.getBtTitle())
                .userDTO(user)
                .draftDay(businessTripDTO.getBtDate())
                .form("출장신청서")
                .tempSaveStatus("Y").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

        // 출장신청서
        BusinessTrip businessTrip = new BusinessTrip();


        businessTrip.document(result);
        businessTrip.user(user);
        businessTrip.btDate(businessTripDTO.getBtDate());
        businessTrip.btPhone(businessTripDTO.getBtPhone());
        businessTrip.btStart(businessTripDTO.getBtStart());
        businessTrip.btFinish(businessTripDTO.getBtFinish());
        businessTrip.btPlace(businessTripDTO.getBtPlace());
        businessTrip.btContent(businessTripDTO.getBtContent());
        businessTrip.btTitle(businessTripDTO.getBtTitle());

        businessTripRepository.save(businessTrip);
        return "등록 성공";
    }

    public String temporarySaveApprovalGeneralDraft(GeneralDraftDTO generalDraftDTO, UserDTO userDTO) {
        System.out.println("userDTO@@@@@@@@@@@@@@@@@@@@ = " + userDTO);
        System.out.println("generalDraftDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + generalDraftDTO);


        User user = modelMapper.map(userDTO, User.class);

        LocalDate now = LocalDate.now();
        generalDraftDTO.setGdDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(generalDraftDTO.getGdTitle())
                .userDTO(user)
                .draftDay(generalDraftDTO.getGdDate())
                .form("기안신청서")
                .tempSaveStatus("Y").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);


        // 일반기안
        GeneralDraft generalDraft = new GeneralDraft();
        generalDraft.gdDate(generalDraftDTO.getGdDate());
        generalDraft.gdTitle(generalDraftDTO.getGdTitle());
        generalDraft.gdContent(generalDraftDTO.getGdContent());
        generalDraft.document(result);
        generalDraft.user(user);

        generalDraftRepository.save(generalDraft);


        return "임시저장 성공";

    }

    // 임시 기안 조회
    public Page<DocumentDTO> findTempSaveDocument(UserDTO userDTO, Criteria cri) {
        // 로그인한 사용자의 정보 가져오기
        System.out.println("userDTO1111111111111111111111111 = " + userDTO);
        User user = modelMapper.map(userDTO, User.class);
        System.out.println("user1111111111111111111111111111 = " + user);

        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("documentCode").descending());

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Page<Document> result = documentRepository.findByUserDTOAndTempSaveStatus(user, "Y", paging);
        Page<DocumentDTO> documentList = result.map(document -> modelMapper.map(document, DocumentDTO.class));



        return documentList;
    }

    public String insertBySelectTempDocument(int documentCode, DayOffApplyDTO dayOffApplyDTO, BusinessTripDTO businessTripDTO, EducationDTO educationDTO, GeneralDraftDTO generalDraftDTO, MultipartFile file, UserDTO userDTO) throws IOException {


        // 로그인한 사용자의 정보 가져오기
        User user = modelMapper.map(userDTO, User.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Document document = documentRepository.findDocumentByDocumentCodeAndUserDTOAndTempSaveStatus(documentCode, user, "Y");

        System.out.println("document=================================== " + document);

        switch (document.getForm()) {
            case "휴가신청서":
                insertDayOffApply(dayOffApplyDTO, file, userDTO, document);
                break;
            case "출장신청서":
                insertBusinessTrip(businessTripDTO, file, userDTO, document);
                break;
            case "교육신청서":
                insertEducation(educationDTO, file, userDTO, document);
                break;
            case "기안신청서":
                insertgeneralDraft(generalDraftDTO, file, userDTO, document);
                break;
            default:
                break;
        }

        return "등록 성공";
    }

    // 대리결재 위임
    @Transactional
    public String insertProxy(ProxyApprovalDTO proxyApprovalDTO, UserDTO userDTO) {

        System.out.println("proxyApprovalDTO =============== " + proxyApprovalDTO);
        System.out.println("userDTO ============= " + userDTO);

        User originUser = modelMapper.map(userDTO, User.class);

        User changeUser = userRepository.findByUserCode(proxyApprovalDTO.getChangeUser().getUserCode());


        ProxyApproval proxyApproval = new ProxyApproval();
        proxyApproval.originUser(originUser);
        proxyApproval.changeUser(changeUser);
        proxyApproval.startDate(proxyApprovalDTO.getStartDate());
        proxyApproval.finishDate(proxyApprovalDTO.getFinishDate());
        proxyApproval.proxyStatus("N");

        proxyApprovalRepository.save(proxyApproval);

        return "등록 성공";
    }

    // 문서 댓글 작성
    @Transactional
    public String insertApprovalComment(int documentCode, ApprovalCommentDTO approvalCommentDTO, UserDTO userDTO) {

        Document document = documentRepository.findByDocumentCode(documentCode);
        User user = modelMapper.map(userDTO, User.class);

        ApprovalComment approvalComment = new ApprovalComment();
        approvalComment.acContent(approvalCommentDTO.getAcContent());
        approvalComment.acDate(LocalDate.now());
        approvalComment.document(document);
        approvalComment.user(user);

        approvalCommentRepository.save(approvalComment);

        return "댓글 등록 성공";
    }

    // 재기안
    public String reDraft(int documentCode, DayOffApplyDTO dayOffApplyDTO, BusinessTripDTO businessTripDTO, EducationDTO educationDTO, GeneralDraftDTO generalDraftDTO, MultipartFile file, UserDTO userDTO) throws IOException {

        // 해당 사용자의 결재 상신 문서 리스트 조회
        Document document1 = documentRepository.findDocumentByDocumentCode(documentCode);

        Document document = new Document();

        document.form(document1.getForm());
        document.userDTO(document1.getUserDTO());
        document.documentTitle(document1.getDocumentTitle());




        System.out.println("document=================================== " + document);

        switch (document.getForm()) {
            case "휴가신청서":
                insertDayOffApply(dayOffApplyDTO, file, userDTO, document);
                break;
            case "출장신청서":
                insertBusinessTrip(businessTripDTO, file, userDTO, document);
                break;
            case "교육신청서":
                insertEducation(educationDTO, file, userDTO, document);
                break;
            case "기안신청서":
                insertgeneralDraft(generalDraftDTO, file, userDTO, document);
                break;
            default:
                break;
        }

        return "재기안 성공";
    }
    // 문서 공유 하기
    public String insertSharedDocument(int documentCode, int userCode) {
        Document document = documentRepository.findByDocumentCode(documentCode);
        User user = userRepository.findByUserCode(userCode);
        SharedDocument sharedDocument2 = sharedDocumentRepository.findByDocumentAndUser(document,user);
        if(sharedDocument2 == null) {
            SharedDocument sharedDocument = new SharedDocument();
            sharedDocument.document(document);
            sharedDocument.user(user);
            sharedDocument.sdStatus("N");

            sharedDocumentRepository.save(sharedDocument);
            return "문서 공유 성공";
        }


        return "이미 중복된 문서공유입니다.";
    }
    // 공유 받은 문서 조회하기
    public Page<DocumentDTO> selectSharedDocument(UserDTO userDTO, Criteria cri) {
        log.info("[ProductService] selectProductListWithPaging Start ===================");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("sdCode").descending());


        Page<SharedDocument> documentCode = sharedDocumentRepository.findByUserUserCode(userDTO.getUserCode(), paging);
        List<Document> documentList = new ArrayList<>();

        for (SharedDocument sharedDocument : documentCode){
            Document document = documentRepository.findByDocumentCode(sharedDocument.getDocument().getDocumentCode());
            // 댓글 조회
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalCommentList);
            if(document != null){
                documentList.add(document);
            }

            }


        // 전체 문서의 수를 얻어옵니다. 이것은 실제 데이터베이스 쿼리를 통해 얻어야 할 수도 있습니다.
        long totalDocuments = documentCode.getTotalElements(); // 이 예에서는 documentList에서 가져옵니다.

        // List<Document>를 Page<DocumentDTO>로 변환
        Page<DocumentDTO> documentDTOPage = new PageImpl<>(
                documentList.stream()
                        .map(document -> modelMapper.map(document, DocumentDTO.class))
                        .collect(Collectors.toList()),
                paging, // 필요한 페이징 정보를 전달합니다.
                totalDocuments // 전체 문서의 수를 전달합니다.
        );

        return documentDTOPage;
    }


    public UserAndTeamDTO findUserDetail(UserDTO userDTO) {
        System.out.println("userDTO=================================== = " + userDTO);
        User user = userRepository.findByUserCode(userDTO.getUserCode());

        UserAndTeam userAndTeam = userAndTeamRepository.findByUserCodeAndTeamTeamCode(user.getUserCode(),user.getTeam().getTeamCode());
        userAndTeam.imgName(IMAGE_URL + userAndTeam.getImgName());
        System.out.println("userAndTeam = " + userAndTeam);
        UserAndTeamDTO UserAndTeamDTO = modelMapper.map(userAndTeam, UserAndTeamDTO.class);
        System.out.println("UserAndTeamDTO = " + UserAndTeamDTO);
        return UserAndTeamDTO;
    }

    public UserAndTeamDTO findByUserCode(int userCode) {

        User user = userRepository.findByUserCode(userCode);

        UserAndTeam userAndTeam = userAndTeamRepository.findByUserCodeAndTeamTeamCodeAndDayOffOffCode(user.getUserCode(),user.getTeam().getTeamCode(),user.getDayoff().getOffCode());
        System.out.println("userAndTeam = " + userAndTeam);
        UserAndTeamDTO UserAndTeamDTO = modelMapper.map(userAndTeam, UserAndTeamDTO.class);
        System.out.println("UserAndTeamDTO = " + UserAndTeamDTO);
        return UserAndTeamDTO;
    }

    public Object selectTempDocumentDetail(int documentCode) {

        Document document = documentRepository.findByDocumentCode(documentCode);

        switch (document.getForm()){
            case"휴가신청서": DayOffApply dayOffApply = dayOffApplyRepository.findByDocument(document);
                DayOffApplyDTO dayOffApplyDTO = modelMapper.map(dayOffApply, DayOffApplyDTO.class);
                return dayOffApplyDTO ;
            case"출장신청서": BusinessTrip businessTrip = businessTripRepository.findByDocument(document);
                BusinessTripDTO businessTripDTO = modelMapper.map(businessTrip, BusinessTripDTO.class);
                return businessTripDTO ;
            case"교육신청서": Education education = educationRepository.findByDocument(document);
                EducationDTO educationDTO = modelMapper.map(education, EducationDTO.class);
                return educationDTO ;
            case"기안신청서": GeneralDraft generalDraft = generalDraftRepository.findByDocument(document);
                GeneralDraftDTO generalDraftDTO = modelMapper.map(generalDraft, GeneralDraftDTO.class);
                return generalDraftDTO ;

        }

        return "";
    }

    public List<ApprovalLineDTO> selectApprovalLine(int documentCode) {
        List<ApprovalLine> approvalLine = approvalLineRepository.findByDocumentDocumentCode(documentCode);
        approvalLine.get(0).getDocument().getUserDTO().profilePath(IMAGE_URL + approvalLine.get(0).getDocument().getUserDTO().getProfilePath());

        List<ApprovalLine> approvalLineList = new ArrayList<>();
        
        for(int i = 0; i < approvalLine.size(); i++){
            approvalLine.get(i).getUser().profilePath(IMAGE_URL + approvalLine.get(i).getUser().getProfilePath());
            
            approvalLineList.add(approvalLine.get(i));
            System.out.println("approvalLine = " + approvalLine.get(i));
        }
        

        return approvalLineList.stream()
                .map(approvalLine1 -> modelMapper.map(approvalLine1, ApprovalLineDTO.class))
                .collect(Collectors.toList());
    }

    public List<ReferenceDTO> selectApprovalRf(int documentCode) {
        List<Reference> reference = referenceRepository.findByDocumentDocumentCode(documentCode);
        System.out.println("reference = " + reference);
        return reference.stream()
                .map(reference1 -> modelMapper.map(reference1, ReferenceDTO.class))
                .collect(Collectors.toList());
    }


    // 대리결재 조회
    public Object selectProxy(UserDTO userDTO) {

        ProxyApproval proxyApproval = proxyApprovalRepository.findByOriginUserUserCodeAndProxyStatus(userDTO.getUserCode(), "N");

        if(proxyApproval != null){
        ProxyApprovalDTO proxyApprovalDTO = modelMapper.map(proxyApproval, ProxyApprovalDTO.class);

        System.out.println("proxyApprovalDTO = " + proxyApprovalDTO);
        proxyApprovalDTO.getChangeUser().setProfilePath(IMAGE_URL + proxyApprovalDTO.getChangeUser().getProfilePath());

        return proxyApprovalDTO;
        }


        return "조회성공";
    }
    @Transactional
    public Object deleteTempApproval(int[] documentCodeList) {
//        int[] documentCodeList = changeUser(documentCode);

        for(int i = 0; i <documentCodeList.length; i++){
            Document document = documentRepository.findByDocumentCode(documentCodeList[i]);
            switch (document.getForm()){
                case"휴가신청서": dayOffApplyRepository.deleteByDocument(document);
                    break;
                case"교육신청서": educationRepository.deleteByDocument(document);
                    break;
                case"출장신청서": businessTripRepository.deleteByDocument(document);
                    break;
                case"기안신청서": generalDraftRepository.deleteByDocument(document);
                    break;
            }
            documentRepository.deleteByDocumentCode(documentCodeList[i]);

        }
        return "삭제 성공";
    }
    @Transactional
    public String updateProxy(int proxyCode) {
        ProxyApproval proxyApproval1 = proxyApprovalRepository.findByProxyCode(proxyCode);

        System.out.println("proxyApproval1 = " + proxyApproval1);

        proxyApproval1.proxyStatus("F");

        proxyApprovalRepository.save(proxyApproval1);
        return "대리결재 위임 취소 성공";
    }

    // 참조 문서 진행중 조회
    public Object selectRfDocument(UserDTO userDTO) {
        List<Reference> reference = referenceRepository.findByUserUserCode(userDTO.getUserCode());

        System.out.println("reference = " + reference);
        List<Document> documentList = new ArrayList<>();
        // 진행중 확인
        for(Reference reference1 : reference){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(reference1.getDocument());

                int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("미결") ){

                    count++;
                    System.out.println("count123123123123 = " + count);
                    System.out.println("approvalLine1 123123123123123123= " + approvalLine2.size());
                }
            }
                    if(count == approvalLine2.size()){
                        Document document = documentRepository.findByDocumentCode(reference1.getDocument().getDocumentCode());
                        documentList.add(document);
                    }
        }



        return documentList.stream()
                .map(documentList1 -> modelMapper.map(documentList1, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    public  List<DocumentDTO> selectRfDocumentComplete(UserDTO userDTO) {

        List<Reference> reference = referenceRepository.findByUserUserCode(userDTO.getUserCode());

        System.out.println("reference = " + reference);
        List<Document> documentList = new ArrayList<>();
        // 진행중 확인
        for(Reference reference1 : reference){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(reference1.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("반려")){

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
                    if(count == approvalLine2.size()){
                        Document document = documentRepository.findByDocumentCode(reference1.getDocument().getDocumentCode());
                        documentList.add(document);
                    }
        }
        return documentList.stream()
                .map(documentList1 -> modelMapper.map(documentList1, DocumentDTO.class))
                .collect(Collectors.toList());

    }

    // 결재한 문서 중에 완료 조회
    public  List<DocumentDTO> selectLineDocumentComplete(UserDTO userDTO) {
        List<ApprovalLine> approvalLines = approvalLineRepository.findByUserUserCode(userDTO.getUserCode());

        List<Document> documentList = new ArrayList<>();
        // 완료 문서 확인
        for(ApprovalLine approvalLine : approvalLines){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(approvalLine.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("반려")){

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
            if(count == approvalLine2.size()){
                Document document = documentRepository.findByDocumentCode(approvalLine.getDocument().getDocumentCode());
                documentList.add(document);
            }

        }

        return documentList.stream()
                .map(documentList1 -> modelMapper.map(documentList1, DocumentDTO.class))
                .collect(Collectors.toList());
    }
    // 결재한 문서 중에 진행중인 문서 조회
    public Object selectLineDocument(UserDTO userDTO) {

        List<ApprovalLine> approvalLines = approvalLineRepository.findByUserUserCode(userDTO.getUserCode());

        List<Document> documentList = new ArrayList<>();
        // 완료 문서 확인
        for(ApprovalLine approvalLine : approvalLines){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(approvalLine.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("미결")){

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
            if(count == approvalLine2.size()){
                Document document = documentRepository.findByDocumentCode(approvalLine.getDocument().getDocumentCode());
                documentList.add(document);
            }

        }

        return documentList.stream()
                .map(documentList1 -> modelMapper.map(documentList1, DocumentDTO.class))
                .collect(Collectors.toList());
    }


    // 내문서함 전체 갯수 조회
    public Object totaldocument(UserDTO userDTO) {


        List<ApprovalLine> approvalLines = approvalLineRepository.findByUserUserCode(userDTO.getUserCode());

        List<Document> documentList = new ArrayList<>();
        // 완료 문서 확인
        for(ApprovalLine approvalLine : approvalLines) {
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(approvalLine.getDocument());

            int count = 0;

            for (ApprovalLine approvalLine1 : approvalLine2) {
                if (approvalLine1.getApprovalLineStatus().equals("결재") || approvalLine1.getApprovalLineStatus().equals("반려")) {

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
            if (count == approvalLine2.size()) {
                Document document = documentRepository.findByDocumentCode(approvalLine.getDocument().getDocumentCode());
                documentList.add(document);
            }
        }

        List<Reference> reference = referenceRepository.findByUserUserCode(userDTO.getUserCode());

        List<Document> documentList2 = new ArrayList<>();
        // 진행중 확인
        for(Reference reference1 : reference){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(reference1.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("반려")){

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
            if(count == approvalLine2.size()){
                Document document = documentRepository.findByDocumentCode(reference1.getDocument().getDocumentCode());
                documentList2.add(document);
            }
        }

        List<Document> documentList3 = documentRepository.findDoc2ByUserDTOUserCode(userDTO.getUserCode());

        System.out.println("documentList = " + documentList);


        // 상태가 반려인 문서
        List<Document> rejectionDocument = new ArrayList<>();
        for (Document document : documentList3) {
            List<Integer> rejectionApprovalLine = approvalLineRepository.rejectionDocument(document.getDocumentCode());
            // 댓글 조회 후 담아주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalCommentList);
            if (!rejectionApprovalLine.isEmpty()) {
                rejectionDocument.add(document);
            }
        }

        List<Document> documentList4 = documentRepository.findDoc2ByUserDTOUserCode(userDTO.getUserCode());

        // 상태가 결재인 문서
        List<Document> approvalComplete = new ArrayList<>();

        // 해당 사용자의 결재 상신 문서 리스트를 순회하며 쿼리를 통해 검색한 결과와 비교하여 결과 리스트에 추가
        List<Integer> completeDocCodes = approvalLineRepository.approvalComplete(userDTO.getUserCode());
        for (Document document : documentList4) {
            // 문서 댓글 조회 후 넣어주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document);
            document.approvalComment(approvalCommentList);

            if (completeDocCodes.contains(document.getDocumentCode())) {
                approvalComplete.add(document);
            }
        }
        int num = approvalComplete.size() + rejectionDocument.size() + documentList2.size() + documentList.size();
        System.out.println("num = " + num);
        return num;

    }

    public Object totaldocumentproceeding(UserDTO userDTO) {

        List<ApprovalLine> approvalLines = approvalLineRepository.findByUserUserCode(userDTO.getUserCode());

        List<Document> documentList = new ArrayList<>();
        // 완료 문서 확인
        for(ApprovalLine approvalLine : approvalLines){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(approvalLine.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("미결")){

                    count++;
                    System.out.println("count123123123123123 = " + count);
                    System.out.println("approvalLine1123123123123123123 = " + approvalLine2.size());
                }
            }
            if(count == approvalLine2.size()){
                Document document = documentRepository.findByDocumentCode(approvalLine.getDocument().getDocumentCode());
                documentList.add(document);
            }

        }

        List<Reference> reference = referenceRepository.findByUserUserCode(userDTO.getUserCode());

        System.out.println("reference = " + reference);
        List<Document> documentList2 = new ArrayList<>();
        // 진행중 확인
        for(Reference reference1 : reference){
            List<ApprovalLine> approvalLine2 = approvalLineRepository.findByDocument(reference1.getDocument());

            int count = 0;

            for(ApprovalLine approvalLine1 : approvalLine2 ){
                if(approvalLine1.getApprovalLineStatus().equals("결재") ||  approvalLine1.getApprovalLineStatus().equals("미결") ){

                    count++;
                    System.out.println("count123123123123 = " + count);
                    System.out.println("approvalLine1 123123123123123123= " + approvalLine2.size());
                }
            }
            if(count == approvalLine2.size()){
                Document document = documentRepository.findByDocumentCode(reference1.getDocument().getDocumentCode());
                documentList2.add(document);
            }
        }


        User changeUser = modelMapper.map(userDTO, User.class);

        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUser(changeUser);
        if (proxyApproval != null && proxyApproval.getProxyStatus().equals("Y")) {
            // 해당 위임받은 결재문서 의 결재 상신 문서 리스트 조회
            List<Document> documents = documentRepository.findByUserDTOUserCode(proxyApproval.getOriginUser().getUserCode());
            // 해당 사용자의 결재 상신 문서 리스트 조회
            List<Document> mydocuments = documentRepository.findByUserDTOUserCode(changeUser.getUserCode());



            // 위임받은 문서
            List<Document> documentList3 = new ArrayList<>();
            for (Document document1 : documents) {
                List<Integer> approvalLines22 = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
                // 문서 댓글 조회후 넣어주기
                List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
                document1.approvalComment(approvalCommentList);
                if (!approvalLines22.isEmpty()) {
                    documentList3.add(document1);

                }
            }
            // 자기 문서
            for (Document document1 : mydocuments) {
                List<Integer> approvalLines22 = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
                // 문서 댓글 조회후 넣어주기
                List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
                document1.approvalComment(approvalCommentList);
                if (!approvalLines22.isEmpty()) {
                    documentList3.add(document1);
                }
            }
        int num = documentList.size() + documentList2.size() + documentList3.size();

            System.out.println("num 111111111111111111= " + num);

            return num;
        }

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documents = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());

        // 결재 상태 중에 미결이 존재하는 문서 리스트 조회
        List<Document> documentList4 = new ArrayList<>();
        for (Document document1 : documents) {
            List<Integer> approvalLines4 = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
            // 문서 댓글 조회후 넣어주기
            List<ApprovalComment> approvalCommentList = approvalCommentRepository.findByDocument(document1);
            document1.approvalComment(approvalCommentList);
            if (!approvalLines4.isEmpty()) {
                documentList4.add(document1);
            }
        }
        int num = documentList.size() + documentList2.size() + documentList4.size();

        System.out.println("num 2222222222222222= " + num);
        return num;
    }

    public Object selectfile(int documentCode) {
        ApprovalFile approvalFile = approvalFileRepository.findByDocumentDocumentCode(documentCode);
        if(approvalFile == null){



        System.out.println("approvalFileDTO111111111111111111111111111111111111111111 = " + approvalFile);
            return "첨부 파일이 없습니다.";
        }
        approvalFile.apFileNameChange(IMAGE_URL + approvalFile.getApFileNameChange());
        System.out.println("approvalFileDTO111111111111111111111111111111111111111111 = " + approvalFile);

        ApprovalFileDTO approvalFileDTO = modelMapper.map(approvalFile, ApprovalFileDTO.class);
        return approvalFileDTO;
    }

    // 대리 결재 시 대리결재자 조건용
    public Object selectProxy2(UserDTO userDTO) {

        ProxyApproval proxyApproval = proxyApprovalRepository.findByChangeUserUserCodeAndProxyStatus(userDTO.getUserCode(),"Y");

        if(proxyApproval != null){
            ProxyApprovalDTO proxyApprovalDTO = modelMapper.map(proxyApproval, ProxyApprovalDTO.class);

            System.out.println("proxyApprovalDTO = " + proxyApprovalDTO);
            proxyApprovalDTO.getChangeUser().setProfilePath(IMAGE_URL + proxyApprovalDTO.getChangeUser().getProfilePath());

            return proxyApprovalDTO;
        }

        return "조회성공";
    }
}

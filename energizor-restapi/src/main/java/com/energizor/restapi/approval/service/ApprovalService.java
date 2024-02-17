package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.*;
import com.energizor.restapi.approval.repository.*;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import com.energizor.restapi.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

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



    @Transactional
    public String insertDayOffApply(DayOffApplyDTO dayOffApplyDTO, MultipartFile file, UserDTO principal) throws IOException {
        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);

        User user1 = userRepository.findByUserCode(principal.getUserCode());
        User user = modelMapper.map(user1, User.class);

        LocalDate now = LocalDate.now();
        dayOffApplyDTO.setOffApplyDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(dayOffApplyDTO.getOffApplyTitle())
                .userDTO(user)
                .draftDay(dayOffApplyDTO.getOffApplyDate())
                .form("휴가신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);



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
                approvalFile.apFilePathOrigin(IMAGE_DIR);
                approvalFile.apFilePathChange(IMAGE_DIR + "/" + storedFileName);
                approvalFile.document(result);

                // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
                approvalFileRepository.save(approvalFile);

        // 참조, 결재선 지정
        int[] rfUser = changeUser(dayOffApplyDTO.getRfUser());
        for(int i=0; i < rfUser.length; i++){
            if(rfUser[i]  > 0){


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

        int[] lineUser = changeUser(dayOffApplyDTO.getLineUser());
        for(int i=0; i < lineUser.length; i++){



            if(lineUser[i]  > 0){


                User userCode1 = userRepository.findByUserCode(lineUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i+1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);
                approvalLine.reason(null);

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
        dayOffApply.offState(dayOffApplyDTO.getOffState());

        System.out.println("dayOffApply : " + dayOffApply);

        DayOffApply result2 = dayOffApplyRepository.save(dayOffApply);
        return "휴가신청서 기안 성공";
    }
    @Transactional
    public String insertBusinessTrip(BusinessTripDTO businessTripDTO, MultipartFile file, UserDTO principal) throws IOException {
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
                .form("출장신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

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
        approvalFile.apFilePathOrigin(IMAGE_DIR);
        approvalFile.apFilePathChange(IMAGE_DIR + "/" + storedFileName);
        approvalFile.document(result);

        // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
        approvalFileRepository.save(approvalFile);

        // 참조, 결재선 지정

        int[] rfUser = changeUser(businessTripDTO.getRfUser());



        for(int i=0; i < rfUser.length; i++){
            if(rfUser[i]  > 0){


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

        int[] lineUser = changeUser(businessTripDTO.getLineUser());


        for(int i=0; i < lineUser.length; i++){

            if(lineUser[i]  > 0){


                User userCode1 = userRepository.findByUserCode(lineUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i+1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);
                approvalLine.reason(null);

                approvalLineRepository.save(approvalLine);

            }

        }

        // 출장신청서
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

        businessTripRepository.save(businessTrip);
        return "등록 성공";
    }

    public String insertEducation(EducationDTO educationDTO, MultipartFile file, UserDTO principal) throws IOException {
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
                .form("교육신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

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
        approvalFile.apFilePathOrigin(IMAGE_DIR);
        approvalFile.apFilePathChange(IMAGE_DIR + "/" + storedFileName);
        approvalFile.document(result);

        // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
        approvalFileRepository.save(approvalFile);



        // 참조, 결재선 지정
        int[] rfUser = changeUser(educationDTO.getRfUser());
        for(int i=0; i < rfUser.length; i++){
            if(rfUser[i]  > 0){


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

        int[] lUser = changeUser(educationDTO.getLineUser());
        for(int i=0; i < lUser.length; i++){



            if(lUser[i]  > 0){


                User userCode1 = userRepository.findByUserCode(lUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);


                System.out.println("userCode================================ " + userCode);


                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i+1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);
                approvalLine.reason(null);

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

    public String insertgeneralDraft(GeneralDraftDTO generalDraftDTO, MultipartFile file, UserDTO principal) throws IOException {

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);
        System.out.println("generalDraftDTO@@@@@@@@@@@@@@@@@@@@@@@@@@@@ = " + generalDraftDTO);


        User user = modelMapper.map(principal, User.class);

        LocalDate now = LocalDate.now();
        generalDraftDTO.setGdDate(now);
        // 기안 -> 기안번호 조회
        Document document = new Document();
        document.documentTitle(generalDraftDTO.getGdTitle())
                .userDTO(user)
                .draftDay(generalDraftDTO.getGdDate())
                .form("교육신청서")
                .tempSaveStatus("N").build();

        System.out.println("document = " + document);

        Document result = documentRepository.save(document);

        System.out.println("result = " + result);

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
        approvalFile.apFilePathOrigin(IMAGE_DIR);
        approvalFile.apFilePathChange(IMAGE_DIR + "/" + storedFileName);
        approvalFile.document(result);

        // ApprovalFile 엔터티를 데이터베이스에 저장합니다.
        approvalFileRepository.save(approvalFile);

        // 참조, 결재선 지정
        int[] rfUser = changeUser(generalDraftDTO.getRfUser());
        for(int i=0; i < rfUser.length; i++){
            if(rfUser[i]  > 0){


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

        int[] lUser = changeUser(generalDraftDTO.getLineUser());
        for(int i=0; i < lUser.length; i++){


            if(lUser[i]  > 0){
                User userCode1 = userRepository.findByUserCode(lUser[i]);
                User userCode = modelMapper.map(userCode1, User.class);

                System.out.println("userCode================================ " + userCode);

                ApprovalLine approvalLine = new ApprovalLine();
                approvalLine.document(result);
                approvalLine.user(userCode);
                approvalLine.sequence(i+1);
                approvalLine.approvalLineStatus("미결");
                approvalLine.processingDate(null);
                approvalLine.reason(null);

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

        return "등록 성공";
    }

    // 들고온 유저코드 분할작업
    public int[] changeUser(String user){
        String[] rfUserArr = user.split(",");
        int[] rfUserIntArr = Arrays.stream(rfUserArr)
                .mapToInt(Integer::parseInt)
                .toArray();

        return rfUserIntArr;
    }

    // 기안한 문서 조회
    public List<DocumentDTO> findDocumentsByUserCode(UserDTO userDTO) {
        // 로그인한 사용자의 정보 가져오기
        System.out.println("userDTO1111111111111111111111111 = " + userDTO);
        User user = modelMapper.map(userDTO, User.class);
        System.out.println("user1111111111111111111111111111 = " + user);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documentList = documentRepository.findByUserDTOAndTempSaveStatus(user, "N");

        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    // 결재 대기 문서 조회
    public List<DocumentDTO> inBoxDocumentByUserCode(UserDTO userDTO) {


        // 문서 코드 목록 가져오기
        List<Integer> inboxDocumentList = documentRepository.inboxDocumentByUserDTO(userDTO.getUserCode());

        System.out.println("inboxDocumentList ============================ " + inboxDocumentList);
        // 문서 코드 목록으로 ApprovalDoc 정보 가져오기
        List<Document> documentList = new ArrayList<>();
        for (int documentCode : inboxDocumentList) {
            System.out.println("documentCode =========================== " + documentCode);
            Document document = documentRepository.findByDocumentCodeAndTempSaveStatus(documentCode,"N");
            System.out.println("document ============================== " + document);
            if(document != null) {
                documentList.add(document);
            }
        }

        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());

    }
    @Transactional
    public String approvement(int documentCode, UserDTO userDTO) {

        //결재 대상 조회
        int approvalLineUser = approvalLineRepository.approvalSubjectUserCode(documentCode);


        // 결재대상 확인
        if (approvalLineUser != userDTO.getUserCode()){
            return "결재 대상이 아닙니다.";
        }


        int userCode = userDTO.getUserCode();
        ApprovalLine approvalLine = approvalLineRepository.findByDocumentDocumentCodeAndUserUserCode(documentCode, userCode);

        System.out.println("approvalLine ================================== " + approvalLine);

        // 결재 상태 업데이트
        approvalLine.processingDate(LocalDateTime.now());
        approvalLine.approvalLineStatus("결재");
        approvalLineRepository.save(approvalLine);

        return "결재 성공";
    }

    @Transactional
    public String rejection(int documentCode, UserDTO userDTO) {

        //결재 대상 조회
        int approvalLineUser = approvalLineRepository.approvalSubjectUserCode(documentCode);


        // 결재대상 확인
        if (approvalLineUser != userDTO.getUserCode()){
            return "결재 대상이 아닙니다.";
        }


        // 대기 상태인 모든 approvalLine 조회
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

    public List<DocumentDTO> approvalProgress(UserDTO userDTO) {


        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documents = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());

        // 결재 상태 중에 미결이 존재하는 문서 리스트 조회
        List<Document> documentList = new ArrayList<>();
        for(Document document1 : documents) {
            List<Integer> approvalLines = approvalLineRepository.findSuspenseApprovalLines(document1.getDocumentCode());
            if(!approvalLines.isEmpty()) {
                documentList.add(document1);
            }
        }
        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    // 결재 완료 문서 조회
    public List<DocumentDTO> approvalComplete(UserDTO userDTO) {

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documentList = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());


        // 상태가 결재인 문서
        List<Document> approvalComplete = new ArrayList<>();

        // 해당 사용자의 결재 상신 문서 리스트를 순회하며 쿼리를 통해 검색한 결과와 비교하여 결과 리스트에 추가
        for (Document document : documentList) {
            List<Integer> completeDocCodes = approvalLineRepository.approvalComplete(userDTO.getUserCode());
            if (completeDocCodes.contains(document.getDocumentCode())) {
                approvalComplete.add(document);
            }
        }



        return approvalComplete.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    // 반려 문서 조회
    public List<DocumentDTO> rejectionInOutbox(UserDTO userDTO) {

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documentList = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());

        System.out.println("documentList = " + documentList);


        // 상태가 결재인 문서
        List<Document> rejectionDocument = new ArrayList<>();
        for (Document document : documentList) {
            List<Integer> rejectionApprovalLine = approvalLineRepository.rejectionDocument(document.getDocumentCode());
            if (!rejectionApprovalLine.isEmpty()) {
                rejectionDocument.add(document);
            }
        }
        return rejectionDocument.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    public List<DocumentDTO> withdrawInOutbox(UserDTO userDTO) {
        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documentList = documentRepository.findByUserDTOUserCode(userDTO.getUserCode());

        System.out.println("documentList = " + documentList);

        // 상태가 결재인 문서
        List<Document> withdrawDocument = new ArrayList<>();
        for (Document document : documentList) {
            List<Integer> withdrawApprovalLine = approvalLineRepository.findWithdrawDocument(document.getDocumentCode());
            if (!withdrawApprovalLine.isEmpty()) {
                withdrawDocument.add(document);
            }
        }
        return withdrawDocument.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String temporarySaveApprovalDayOff(DayOffApplyDTO dayOffApplyDTO, UserDTO userDTO)  {

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
        dayOffApply.offState(dayOffApplyDTO.getOffState());

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


        businessTrip.documentDTO(result);
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
                .form("교육신청서")
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

        return "등록 성공";

    }


    public List<DocumentDTO> findTempSaveDocument(UserDTO userDTO) {
        // 로그인한 사용자의 정보 가져오기
        System.out.println("userDTO1111111111111111111111111 = " + userDTO);
        User user = modelMapper.map(userDTO, User.class);
        System.out.println("user1111111111111111111111111111 = " + user);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<Document> documentList = documentRepository.findByUserDTOAndTempSaveStatus(user, "Y");

        return documentList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList());
    }
}

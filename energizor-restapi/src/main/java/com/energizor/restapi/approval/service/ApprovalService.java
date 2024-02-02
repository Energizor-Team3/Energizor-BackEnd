package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.dto.*;
import com.energizor.restapi.approval.entity.*;
import com.energizor.restapi.approval.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final BusinessTripRepository businessTripRepository;
    private final DayOffApplyRepository dayOffApplyRepository;
    private final EducationRepository educationRepository;
    private final GeneralDraftRepository generalDraftRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository, BusinessTripRepository businessTripRepository, DayOffApplyRepository dayOffApplyRepository, EducationRepository educationRepository, GeneralDraftRepository generalDraftRepository, ModelMapper modelMapper) {
        this.approvalRepository = approvalRepository;
        this.businessTripRepository = businessTripRepository;
        this.dayOffApplyRepository = dayOffApplyRepository;
        this.educationRepository = educationRepository;
        this.generalDraftRepository = generalDraftRepository;
        this.modelMapper = modelMapper;
    }


    public List<DocumentDTO> selectApprovalList() {
        log.info("approvalservice 시작  selectApprovalList================================");

        List<Document> selectApprovalList = approvalRepository.findByDocumentCode(1);

        List<DocumentDTO> documentDTOList = selectApprovalList.stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class)).collect(Collectors.toList());


        for(int i = 0; i < documentDTOList.size(); i++){
            documentDTOList.get(i);
        }
        log.info("[ProductService] selectProductList End ===================");
        return documentDTOList;
    }
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
}

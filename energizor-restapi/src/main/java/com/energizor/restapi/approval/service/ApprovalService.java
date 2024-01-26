package com.energizor.restapi.approval.service;

import com.energizor.restapi.approval.repository.ApprovalRepository;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {
    private ApprovalRepository approvalRepository;

    public ApprovalService(ApprovalRepository approvalRepository) {
        this.approvalRepository = approvalRepository;
    }

    
}

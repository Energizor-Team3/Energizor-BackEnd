package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalFile;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalFileRepository extends JpaRepository<ApprovalFile, Integer> {


    ApprovalFile findByDocumentDocumentCode(int documentCode);
}

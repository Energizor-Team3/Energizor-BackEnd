package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalComment;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Document, Integer> {


    List<Document> findByDocumentCode(int documentCode);
}

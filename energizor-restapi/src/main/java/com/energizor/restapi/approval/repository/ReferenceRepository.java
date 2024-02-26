package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalLine;
import com.energizor.restapi.approval.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceRepository extends JpaRepository<Reference, Integer> {


    List<Reference> findByDocumentDocumentCode(int documentCode);


    List<Reference> findByUserUserCode(int userCode);
}

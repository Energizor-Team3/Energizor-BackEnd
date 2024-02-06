package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalLine;
import com.energizor.restapi.approval.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceRepository extends JpaRepository<Reference, Integer> {



}

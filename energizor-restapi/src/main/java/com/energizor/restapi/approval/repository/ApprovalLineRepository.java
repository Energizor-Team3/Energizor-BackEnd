package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalLine;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Integer> {



}

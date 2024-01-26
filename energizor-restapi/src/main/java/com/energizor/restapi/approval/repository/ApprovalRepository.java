package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<ApprovalComment, Integer> {
}

package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.entity.ProxyApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProxyApprovalRepository extends JpaRepository<ProxyApproval, Integer> {



}

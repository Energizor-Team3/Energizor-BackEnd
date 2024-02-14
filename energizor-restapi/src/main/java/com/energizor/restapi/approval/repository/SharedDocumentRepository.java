package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Reference;
import com.energizor.restapi.approval.entity.SharedDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedDocumentRepository extends JpaRepository<SharedDocument, Integer> {


    Page<SharedDocument> findByUserUserCode(int userCode, Pageable paging);
}

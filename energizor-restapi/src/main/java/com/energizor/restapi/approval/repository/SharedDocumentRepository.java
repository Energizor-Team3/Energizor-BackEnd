package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Reference;
import com.energizor.restapi.approval.entity.SharedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedDocumentRepository extends JpaRepository<SharedDocument, Integer> {



}

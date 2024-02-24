package com.energizor.restapi.approval.repository;


import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.entity.GeneralDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralDraftRepository extends JpaRepository<GeneralDraft, Integer> {
    GeneralDraft findByDocument(Document document);
}

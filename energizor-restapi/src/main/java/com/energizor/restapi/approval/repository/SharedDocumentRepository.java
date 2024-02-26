package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.entity.Reference;
import com.energizor.restapi.approval.entity.SharedDocument;
import com.energizor.restapi.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedDocumentRepository extends JpaRepository<SharedDocument, Integer> {


    Page<SharedDocument> findByUserUserCode(int userCode, Pageable paging);

    SharedDocument findByDocumentAndUser(Document document, User user);
}

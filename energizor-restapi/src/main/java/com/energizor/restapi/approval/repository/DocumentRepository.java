package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Document;

import com.energizor.restapi.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {





    Page<Document> findByUserDTOAndTempSaveStatus(User user,String n, Pageable paging);


    @Query(value =
            "SELECT " +
                    "document_code " +
                    "FROM approvalline a " +
                    "WHERE user_code = :userCode " +
                    "AND approvalline_status = '미결' " +
                    "AND sequence = (" +
                    "SELECT " +
                    "MIN(sequence) " +
                    "FROM approvalline b " +
                    "WHERE " +
                    "a.document_code = b.document_code " +
                    "AND a.approvalline_status = b.approvalline_status)",
            nativeQuery = true)
    List<Integer> inboxDocumentByUserDTO(int userCode);

    Document findByDocumentCodeAndTempSaveStatus(int documentCode, String n);


    List<Document> findByUserDTOUserCode(int userCode);

    Document findByDocumentCodeAndForm(int documentCode, String 휴가신청서);


    Document findDocumentByDocumentCodeAndUserDTOAndTempSaveStatus(int documentCode, User user, String y);

    Document findByDocumentCode(int documentCode);

    Document findDocumentByDocumentCode(int documentCode);


    Page<Document> findDocByUserDTOUserCode(int userCode, Pageable paging);



}

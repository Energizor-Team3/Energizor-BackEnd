package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.ApprovalLine;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Integer> {


    @Query(value =
            "SELECT " +
                    "user_code " +
                    "FROM approvalline " +
                    "WHERE document_code = :documentCode " +
                    "AND approvalline_status = '미결' " +
                    "ORDER BY sequence ASC " +
                    "LIMIT 1",
            nativeQuery = true)
    int approvalSubjectUserCode(int documentCode);






    ApprovalLine findByDocumentDocumentCodeAndUserUserCode(int documentCode, int userCode);

    @Query(value =
            "SELECT " +
                    "approvalline_code " +
                    "FROM approvalline " +
                    "WHERE document_code = :documentCode " +
                    "AND approvalline_status = '미결' ",
            nativeQuery = true)
    List<Integer> findLineUser(int documentCode);


    List<ApprovalLine> findByDocumentDocumentCode(int documentCode);


    @Query(value =
            "SELECT " +
                    "approvalline_code " +
                    "FROM approvalline " +
                    "WHERE document_code = :documentCode " +
                    "AND approvalline_status = '미결'",
            nativeQuery = true)
    List<Integer> findSuspenseApprovalLines(int documentCode);

    @Query(value =
            "SELECT a.document_code " +
                    "FROM document a " +
                    "INNER JOIN approvalline b ON a.document_code = b.document_code " +
                    "WHERE a.user_code = :userCode " +
                    "AND b.sequence = (" +
                    "SELECT MAX(sequence) " +
                    "FROM approvalline " +
                    "WHERE document_code = a.document_code) " +
                    "AND b.approvalline_status = '결재'",
            nativeQuery = true)
    List<Integer> approvalComplete(int userCode);


    @Query(value =
            "SELECT " +
                    "approvalline_code " +
                    "FROM approvalline " +
                    "WHERE document_code = :documentCode " +
                    "AND approvalline_status = '반려'",
            nativeQuery = true)
    List<Integer> rejectionDocument(int documentCode);

    @Query(value =
            "SELECT " +
                    "approvalline_code " +
                    "FROM approvalline " +
                    "WHERE document_code = :documentCode " +
                    "AND approvalline_status = '회수'",
            nativeQuery = true)
    List<Integer> findWithdrawDocument(int documentCode);
}

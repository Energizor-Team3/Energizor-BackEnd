package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query(value = "SELECT * FROM ",
            nativeQuery = true)
    List<Document> x();
}

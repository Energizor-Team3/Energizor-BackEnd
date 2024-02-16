package com.energizor.restapi.approval.repository;


import com.energizor.restapi.approval.entity.BusinessTrip;
import com.energizor.restapi.approval.entity.DayOffApply;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOffApplyRepository extends JpaRepository<DayOffApply, Integer> {
    DayOffApply findByDocument(Document document);
}

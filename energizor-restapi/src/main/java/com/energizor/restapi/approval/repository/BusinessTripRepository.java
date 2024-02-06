package com.energizor.restapi.approval.repository;


import com.energizor.restapi.approval.entity.BusinessTrip;
import com.energizor.restapi.approval.entity.GeneralDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Integer> {
}

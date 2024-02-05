package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.DayOff;
import com.energizor.restapi.approval.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff, Integer> {



}

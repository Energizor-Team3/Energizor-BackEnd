package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.AllGroupList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllGroupRepository extends JpaRepository<AllGroupList, Integer> {
}

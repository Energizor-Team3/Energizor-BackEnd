package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.AllGroupList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllGroupRepository extends JpaRepository<AllGroupList, Integer> {

}

package com.energizor.restapi.main.repository;

import com.energizor.restapi.main.entity.Main;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<Main, Integer> {
}

package com.energizor.restapi.contact.repository;

import com.energizor.restapi.contact.entity.CompanyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyContactRepository extends JpaRepository<CompanyContact, Integer> {
}

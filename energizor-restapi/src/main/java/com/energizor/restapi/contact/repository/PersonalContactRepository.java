package com.energizor.restapi.contact.repository;

import com.energizor.restapi.contact.entity.PersonalContact;
import com.energizor.restapi.contact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalContactRepository extends JpaRepository<PersonalContact, Integer> {
    List<PersonalContact> findByUser(User user);
}

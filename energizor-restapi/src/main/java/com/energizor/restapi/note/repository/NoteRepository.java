package com.energizor.restapi.note.repository;

import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<User, Integer> {
}

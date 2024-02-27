package com.energizor.restapi.note.repository;

import com.energizor.restapi.note.entity.SendNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendNoteRepository extends JpaRepository<SendNote, Integer> {
}

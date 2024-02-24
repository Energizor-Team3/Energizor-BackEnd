package com.energizor.restapi.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name="register_date",updatable=false)
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name="update_date")
    LocalDateTime updateDate;

    @Column(name="delete_date", nullable = true)
    private LocalDateTime deleteDate;

    public BaseEntity registerDate(LocalDateTime registerDate) {
        this.registerDate=registerDate;
        return this;
    }

    public BaseEntity updateDate(LocalDateTime updateDate) {
        this.updateDate=updateDate;
        return this;
    }

    public BaseEntity deleteDate(LocalDateTime deleteDate) {
        this.deleteDate=deleteDate;
        return this;
    }
}

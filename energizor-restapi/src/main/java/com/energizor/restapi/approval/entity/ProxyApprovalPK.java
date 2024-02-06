package com.energizor.restapi.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ProxyApprovalPK implements Serializable {
    @Column(name = "document_code")
    private int documentCode;
    @Column(name = "change_user")
    private int changeUser;
    @Column(name = "origin_user")
    private int originUser;


}

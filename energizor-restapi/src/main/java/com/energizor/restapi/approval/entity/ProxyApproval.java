package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@Table(name = "proxyapproval")
@ToString
@AllArgsConstructor
public class ProxyApproval {

    @EmbeddedId
    private ProxyApprovalPK proxyApprovalPK;

    public ProxyApproval() {
    }
}

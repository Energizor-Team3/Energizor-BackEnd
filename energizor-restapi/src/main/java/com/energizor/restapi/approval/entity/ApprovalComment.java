package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "approvalcomment")
public class ApprovalComment {
    @Id
    @Column(name = "ac_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acCode;
    @Column(name = "ac_content")
    private String acContent;
    @Column(name = "ac_date")
    private Date acDate;
    @ManyToOne
    @JoinColumn(name = "document_code", referencedColumnName = "document_code")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
}

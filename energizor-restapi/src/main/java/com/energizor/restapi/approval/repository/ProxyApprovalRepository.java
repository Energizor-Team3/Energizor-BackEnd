package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.approval.entity.ProxyApproval;
import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProxyApprovalRepository extends JpaRepository<ProxyApproval, Integer> {


    ProxyApproval findByChangeUser(User changeUser);

    ProxyApproval findByChangeUserUserCode(int userCode);



    ProxyApproval findByOriginUserUserCodeAndProxyStatus(int userCode, String n);

    ProxyApproval findByProxyCode(int proxyCode);
}

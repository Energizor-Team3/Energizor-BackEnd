package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProxyApproval is a Querydsl query type for ProxyApproval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProxyApproval extends EntityPathBase<ProxyApproval> {

    private static final long serialVersionUID = -1843149485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProxyApproval proxyApproval = new QProxyApproval("proxyApproval");

    public final com.energizor.restapi.users.entity.QUser changeUser;

    public final DatePath<java.time.LocalDate> finishDate = createDate("finishDate", java.time.LocalDate.class);

    public final com.energizor.restapi.users.entity.QUser originUser;

    public final NumberPath<Integer> proxyCode = createNumber("proxyCode", Integer.class);

    public final StringPath proxyStatus = createString("proxyStatus");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QProxyApproval(String variable) {
        this(ProxyApproval.class, forVariable(variable), INITS);
    }

    public QProxyApproval(Path<? extends ProxyApproval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProxyApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProxyApproval(PathMetadata metadata, PathInits inits) {
        this(ProxyApproval.class, metadata, inits);
    }

    public QProxyApproval(Class<? extends ProxyApproval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.changeUser = inits.isInitialized("changeUser") ? new com.energizor.restapi.users.entity.QUser(forProperty("changeUser"), inits.get("changeUser")) : null;
        this.originUser = inits.isInitialized("originUser") ? new com.energizor.restapi.users.entity.QUser(forProperty("originUser"), inits.get("originUser")) : null;
    }

}


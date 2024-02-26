package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApprovalLine is a Querydsl query type for ApprovalLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApprovalLine extends EntityPathBase<ApprovalLine> {

    private static final long serialVersionUID = -2082121195L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApprovalLine approvalLine = new QApprovalLine("approvalLine");

    public final NumberPath<Integer> approvalLineCode = createNumber("approvalLineCode", Integer.class);

    public final StringPath approvalLineStatus = createString("approvalLineStatus");

    public final QDocument document;

    public final DateTimePath<java.time.LocalDateTime> processingDate = createDateTime("processingDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final com.energizor.restapi.users.entity.QUser user;

    public QApprovalLine(String variable) {
        this(ApprovalLine.class, forVariable(variable), INITS);
    }

    public QApprovalLine(Path<? extends ApprovalLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApprovalLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApprovalLine(PathMetadata metadata, PathInits inits) {
        this(ApprovalLine.class, metadata, inits);
    }

    public QApprovalLine(Class<? extends ApprovalLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApprovalComment is a Querydsl query type for ApprovalComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApprovalComment extends EntityPathBase<ApprovalComment> {

    private static final long serialVersionUID = 218760350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApprovalComment approvalComment = new QApprovalComment("approvalComment");

    public final NumberPath<Integer> acCode = createNumber("acCode", Integer.class);

    public final StringPath acContent = createString("acContent");

    public final DatePath<java.time.LocalDate> acDate = createDate("acDate", java.time.LocalDate.class);

    public final QDocument document;

    public final com.energizor.restapi.users.entity.QUser user;

    public QApprovalComment(String variable) {
        this(ApprovalComment.class, forVariable(variable), INITS);
    }

    public QApprovalComment(Path<? extends ApprovalComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApprovalComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApprovalComment(PathMetadata metadata, PathInits inits) {
        this(ApprovalComment.class, metadata, inits);
    }

    public QApprovalComment(Class<? extends ApprovalComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


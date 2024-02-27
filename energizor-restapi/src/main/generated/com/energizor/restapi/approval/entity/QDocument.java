package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 800967001L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final ListPath<ApprovalComment, QApprovalComment> approvalComment = this.<ApprovalComment, QApprovalComment>createList("approvalComment", ApprovalComment.class, QApprovalComment.class, PathInits.DIRECT2);

    public final NumberPath<Integer> documentCode = createNumber("documentCode", Integer.class);

    public final StringPath documentTitle = createString("documentTitle");

    public final DatePath<java.time.LocalDate> draftDay = createDate("draftDay", java.time.LocalDate.class);

    public final StringPath form = createString("form");

    public final StringPath tempSaveStatus = createString("tempSaveStatus");

    public final com.energizor.restapi.users.entity.QUser userDTO;

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userDTO = inits.isInitialized("userDTO") ? new com.energizor.restapi.users.entity.QUser(forProperty("userDTO"), inits.get("userDTO")) : null;
    }

}


package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApprovalFile is a Querydsl query type for ApprovalFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApprovalFile extends EntityPathBase<ApprovalFile> {

    private static final long serialVersionUID = -2082300003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApprovalFile approvalFile = new QApprovalFile("approvalFile");

    public final NumberPath<Integer> apFileCode = createNumber("apFileCode", Integer.class);

    public final DateTimePath<java.util.Date> apFileDate = createDateTime("apFileDate", java.util.Date.class);

    public final StringPath apFileNameChange = createString("apFileNameChange");

    public final StringPath apFileNameOrigin = createString("apFileNameOrigin");

    public final StringPath apFileStatus = createString("apFileStatus");

    public final QDocument document;

    public QApprovalFile(String variable) {
        this(ApprovalFile.class, forVariable(variable), INITS);
    }

    public QApprovalFile(Path<? extends ApprovalFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApprovalFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApprovalFile(PathMetadata metadata, PathInits inits) {
        this(ApprovalFile.class, metadata, inits);
    }

    public QApprovalFile(Class<? extends ApprovalFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}


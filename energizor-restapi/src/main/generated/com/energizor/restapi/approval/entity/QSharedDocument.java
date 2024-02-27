package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharedDocument is a Querydsl query type for SharedDocument
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharedDocument extends EntityPathBase<SharedDocument> {

    private static final long serialVersionUID = 611909214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharedDocument sharedDocument = new QSharedDocument("sharedDocument");

    public final QDocument document;

    public final NumberPath<Integer> sdCode = createNumber("sdCode", Integer.class);

    public final StringPath sdStatus = createString("sdStatus");

    public final com.energizor.restapi.users.entity.QUser user;

    public QSharedDocument(String variable) {
        this(SharedDocument.class, forVariable(variable), INITS);
    }

    public QSharedDocument(Path<? extends SharedDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharedDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharedDocument(PathMetadata metadata, PathInits inits) {
        this(SharedDocument.class, metadata, inits);
    }

    public QSharedDocument(Class<? extends SharedDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


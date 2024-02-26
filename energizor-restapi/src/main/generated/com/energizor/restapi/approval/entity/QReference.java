package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReference is a Querydsl query type for Reference
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReference extends EntityPathBase<Reference> {

    private static final long serialVersionUID = 1486442189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReference reference = new QReference("reference");

    public final QDocument document;

    public final NumberPath<Integer> referenceCode = createNumber("referenceCode", Integer.class);

    public final StringPath referenceStatus = createString("referenceStatus");

    public final com.energizor.restapi.users.entity.QUser user;

    public QReference(String variable) {
        this(Reference.class, forVariable(variable), INITS);
    }

    public QReference(Path<? extends Reference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReference(PathMetadata metadata, PathInits inits) {
        this(Reference.class, metadata, inits);
    }

    public QReference(Class<? extends Reference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


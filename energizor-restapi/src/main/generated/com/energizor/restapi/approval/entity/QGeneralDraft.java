package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeneralDraft is a Querydsl query type for GeneralDraft
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeneralDraft extends EntityPathBase<GeneralDraft> {

    private static final long serialVersionUID = -846038313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGeneralDraft generalDraft = new QGeneralDraft("generalDraft");

    public final QDocument document;

    public final NumberPath<Integer> gdCode = createNumber("gdCode", Integer.class);

    public final StringPath gdContent = createString("gdContent");

    public final DatePath<java.time.LocalDate> gdDate = createDate("gdDate", java.time.LocalDate.class);

    public final StringPath gdTitle = createString("gdTitle");

    public final com.energizor.restapi.users.entity.QUser user;

    public QGeneralDraft(String variable) {
        this(GeneralDraft.class, forVariable(variable), INITS);
    }

    public QGeneralDraft(Path<? extends GeneralDraft> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGeneralDraft(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGeneralDraft(PathMetadata metadata, PathInits inits) {
        this(GeneralDraft.class, metadata, inits);
    }

    public QGeneralDraft(Class<? extends GeneralDraft> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEducation is a Querydsl query type for Education
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEducation extends EntityPathBase<Education> {

    private static final long serialVersionUID = 2120841002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEducation education = new QEducation("education");

    public final QDocument document;

    public final NumberPath<Integer> eduCode = createNumber("eduCode", Integer.class);

    public final StringPath eduContent = createString("eduContent");

    public final DatePath<java.time.LocalDate> eduDate = createDate("eduDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> eduFinish = createDate("eduFinish", java.time.LocalDate.class);

    public final StringPath eduInstitution = createString("eduInstitution");

    public final StringPath eduName = createString("eduName");

    public final NumberPath<Integer> eduPrice = createNumber("eduPrice", Integer.class);

    public final DatePath<java.time.LocalDate> eduStart = createDate("eduStart", java.time.LocalDate.class);

    public final StringPath eduTitle = createString("eduTitle");

    public final com.energizor.restapi.users.entity.QUser user;

    public QEducation(String variable) {
        this(Education.class, forVariable(variable), INITS);
    }

    public QEducation(Path<? extends Education> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEducation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEducation(PathMetadata metadata, PathInits inits) {
        this(Education.class, metadata, inits);
    }

    public QEducation(Class<? extends Education> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


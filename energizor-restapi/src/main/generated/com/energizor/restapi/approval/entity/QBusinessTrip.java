package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBusinessTrip is a Querydsl query type for BusinessTrip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessTrip extends EntityPathBase<BusinessTrip> {

    private static final long serialVersionUID = 1316319555L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBusinessTrip businessTrip = new QBusinessTrip("businessTrip");

    public final NumberPath<Integer> btCode = createNumber("btCode", Integer.class);

    public final StringPath btContent = createString("btContent");

    public final DatePath<java.time.LocalDate> btDate = createDate("btDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> btFinish = createDate("btFinish", java.time.LocalDate.class);

    public final StringPath btPhone = createString("btPhone");

    public final StringPath btPlace = createString("btPlace");

    public final DatePath<java.time.LocalDate> btStart = createDate("btStart", java.time.LocalDate.class);

    public final StringPath btTitle = createString("btTitle");

    public final QDocument document;

    public final com.energizor.restapi.users.entity.QUser user;

    public QBusinessTrip(String variable) {
        this(BusinessTrip.class, forVariable(variable), INITS);
    }

    public QBusinessTrip(Path<? extends BusinessTrip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBusinessTrip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBusinessTrip(PathMetadata metadata, PathInits inits) {
        this(BusinessTrip.class, metadata, inits);
    }

    public QBusinessTrip(Class<? extends BusinessTrip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


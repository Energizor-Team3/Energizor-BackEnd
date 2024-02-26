package com.energizor.restapi.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommute is a Querydsl query type for Commute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommute extends EntityPathBase<Commute> {

    private static final long serialVersionUID = 282039606L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommute commute = new QCommute("commute");

    public final NumberPath<Integer> cCode = createNumber("cCode", Integer.class);

    public final DatePath<java.time.LocalDate> cDate = createDate("cDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> cEndTime = createTime("cEndTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> cStartTime = createTime("cStartTime", java.time.LocalTime.class);

    public final StringPath cState = createString("cState");

    public final QUser user;

    public QCommute(String variable) {
        this(Commute.class, forVariable(variable), INITS);
    }

    public QCommute(Path<? extends Commute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommute(PathMetadata metadata, PathInits inits) {
        this(Commute.class, metadata, inits);
    }

    public QCommute(Class<? extends Commute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


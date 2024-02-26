package com.energizor.restapi.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayOff is a Querydsl query type for DayOff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayOff extends EntityPathBase<DayOff> {

    private static final long serialVersionUID = -1213833000L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayOff dayOff = new QDayOff("dayOff");

    public final NumberPath<Integer> offCode = createNumber("offCode", Integer.class);

    public final NumberPath<Integer> offCount = createNumber("offCount", Integer.class);

    public final NumberPath<Integer> offUsed = createNumber("offUsed", Integer.class);

    public final ComparablePath<java.time.Year> offYear = createComparable("offYear", java.time.Year.class);

    public final com.energizor.restapi.users.entity.QUser user;

    public QDayOff(String variable) {
        this(DayOff.class, forVariable(variable), INITS);
    }

    public QDayOff(Path<? extends DayOff> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayOff(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayOff(PathMetadata metadata, PathInits inits) {
        this(DayOff.class, metadata, inits);
    }

    public QDayOff(Class<? extends DayOff> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


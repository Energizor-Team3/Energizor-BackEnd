package com.energizor.restapi.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayoff is a Querydsl query type for Dayoff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayoff extends EntityPathBase<Dayoff> {

    private static final long serialVersionUID = -1810475878L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayoff dayoff = new QDayoff("dayoff");

    public final NumberPath<Integer> offCode = createNumber("offCode", Integer.class);

    public final NumberPath<Integer> offCount = createNumber("offCount", Integer.class);

    public final NumberPath<Integer> offUsed = createNumber("offUsed", Integer.class);

    public final ComparablePath<java.time.Year> offYear = createComparable("offYear", java.time.Year.class);

    public final QUser user;

    public QDayoff(String variable) {
        this(Dayoff.class, forVariable(variable), INITS);
    }

    public QDayoff(Path<? extends Dayoff> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayoff(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayoff(PathMetadata metadata, PathInits inits) {
        this(Dayoff.class, metadata, inits);
    }

    public QDayoff(Class<? extends Dayoff> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


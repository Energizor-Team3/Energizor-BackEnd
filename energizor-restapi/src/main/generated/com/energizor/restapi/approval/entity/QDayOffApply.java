package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayOffApply is a Querydsl query type for DayOffApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayOffApply extends EntityPathBase<DayOffApply> {

    private static final long serialVersionUID = 973714941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayOffApply dayOffApply = new QDayOffApply("dayOffApply");

    public final com.energizor.restapi.users.entity.QDayoff dayoff;

    public final QDocument document;

    public final NumberPath<Integer> offApplyCode = createNumber("offApplyCode", Integer.class);

    public final DatePath<java.time.LocalDate> offApplyDate = createDate("offApplyDate", java.time.LocalDate.class);

    public final StringPath offApplyTitle = createString("offApplyTitle");

    public final NumberPath<Integer> offDay = createNumber("offDay", Integer.class);

    public final DatePath<java.time.LocalDate> offEnd = createDate("offEnd", java.time.LocalDate.class);

    public final StringPath offReason = createString("offReason");

    public final DatePath<java.time.LocalDate> offStart = createDate("offStart", java.time.LocalDate.class);

    public final StringPath offState = createString("offState");

    public final com.energizor.restapi.users.entity.QUser user;

    public QDayOffApply(String variable) {
        this(DayOffApply.class, forVariable(variable), INITS);
    }

    public QDayOffApply(Path<? extends DayOffApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayOffApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayOffApply(PathMetadata metadata, PathInits inits) {
        this(DayOffApply.class, metadata, inits);
    }

    public QDayOffApply(Class<? extends DayOffApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dayoff = inits.isInitialized("dayoff") ? new com.energizor.restapi.users.entity.QDayoff(forProperty("dayoff"), inits.get("dayoff")) : null;
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


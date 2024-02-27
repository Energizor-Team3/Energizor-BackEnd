package com.energizor.restapi.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDayOff is a Querydsl query type for DayOff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayOff extends EntityPathBase<DayOff> {

    private static final long serialVersionUID = -1637441897L;

    public static final QDayOff dayOff = new QDayOff("dayOff");

    public final NumberPath<Integer> offCode = createNumber("offCode", Integer.class);

    public final NumberPath<Integer> offCount = createNumber("offCount", Integer.class);

    public final NumberPath<Integer> offUsed = createNumber("offUsed", Integer.class);

    public final ComparablePath<java.time.Year> offYear = createComparable("offYear", java.time.Year.class);

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public QDayOff(String variable) {
        super(DayOff.class, forVariable(variable));
    }

    public QDayOff(Path<? extends DayOff> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDayOff(PathMetadata metadata) {
        super(DayOff.class, metadata);
    }

}


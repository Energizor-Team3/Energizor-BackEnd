package com.energizor.restapi.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMeet is a Querydsl query type for Meet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeet extends EntityPathBase<Meet> {

    private static final long serialVersionUID = -948270958L;

    public static final QMeet meet = new QMeet("meet");

    public final NumberPath<Integer> meetCode = createNumber("meetCode", Integer.class);

    public final StringPath meetName = createString("meetName");

    public QMeet(String variable) {
        super(Meet.class, forVariable(variable));
    }

    public QMeet(Path<? extends Meet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeet(PathMetadata metadata) {
        super(Meet.class, metadata);
    }

}


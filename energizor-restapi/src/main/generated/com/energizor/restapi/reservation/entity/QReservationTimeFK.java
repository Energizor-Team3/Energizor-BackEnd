package com.energizor.restapi.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationTimeFK is a Querydsl query type for ReservationTimeFK
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationTimeFK extends EntityPathBase<ReservationTimeFK> {

    private static final long serialVersionUID = -1664147245L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationTimeFK reservationTimeFK = new QReservationTimeFK("reservationTimeFK");

    public final QReservationTimePK id;

    public final TimePath<java.sql.Time> meetTime = createTime("meetTime", java.sql.Time.class);

    public QReservationTimeFK(String variable) {
        this(ReservationTimeFK.class, forVariable(variable), INITS);
    }

    public QReservationTimeFK(Path<? extends ReservationTimeFK> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationTimeFK(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationTimeFK(PathMetadata metadata, PathInits inits) {
        this(ReservationTimeFK.class, metadata, inits);
    }

    public QReservationTimeFK(Class<? extends ReservationTimeFK> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QReservationTimePK(forProperty("id")) : null;
    }

}


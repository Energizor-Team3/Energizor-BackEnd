package com.energizor.restapi.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationTimePK is a Querydsl query type for ReservationTimePK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReservationTimePK extends BeanPath<ReservationTimePK> {

    private static final long serialVersionUID = -1664146935L;

    public static final QReservationTimePK reservationTimePK = new QReservationTimePK("reservationTimePK");

    public final TimePath<java.sql.Time> meetTime = createTime("meetTime", java.sql.Time.class);

    public final NumberPath<Integer> reservationCode = createNumber("reservationCode", Integer.class);

    public QReservationTimePK(String variable) {
        super(ReservationTimePK.class, forVariable(variable));
    }

    public QReservationTimePK(Path<? extends ReservationTimePK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationTimePK(PathMetadata metadata) {
        super(ReservationTimePK.class, metadata);
    }

}


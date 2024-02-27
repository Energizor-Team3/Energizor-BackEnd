package com.energizor.restapi.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendee is a Querydsl query type for Attendee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendee extends EntityPathBase<Attendee> {

    private static final long serialVersionUID = 1390945157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendee attendee = new QAttendee("attendee");

    public final NumberPath<Integer> attCode = createNumber("attCode", Integer.class);

    public final QReservation reservation;

    public final com.energizor.restapi.users.entity.QUser userCode;

    public QAttendee(String variable) {
        this(Attendee.class, forVariable(variable), INITS);
    }

    public QAttendee(Path<? extends Attendee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendee(PathMetadata metadata, PathInits inits) {
        this(Attendee.class, metadata, inits);
    }

    public QAttendee(Class<? extends Attendee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new QReservation(forProperty("reservation"), inits.get("reservation")) : null;
        this.userCode = inits.isInitialized("userCode") ? new com.energizor.restapi.users.entity.QUser(forProperty("userCode"), inits.get("userCode")) : null;
    }

}


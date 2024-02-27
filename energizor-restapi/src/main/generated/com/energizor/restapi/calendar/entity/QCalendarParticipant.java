package com.energizor.restapi.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCalendarParticipant is a Querydsl query type for CalendarParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarParticipant extends EntityPathBase<CalendarParticipant> {

    private static final long serialVersionUID = -1763048388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCalendarParticipant calendarParticipant = new QCalendarParticipant("calendarParticipant");

    public final QCalendarParticipantPK calParticipant;

    public QCalendarParticipant(String variable) {
        this(CalendarParticipant.class, forVariable(variable), INITS);
    }

    public QCalendarParticipant(Path<? extends CalendarParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCalendarParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCalendarParticipant(PathMetadata metadata, PathInits inits) {
        this(CalendarParticipant.class, metadata, inits);
    }

    public QCalendarParticipant(Class<? extends CalendarParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calParticipant = inits.isInitialized("calParticipant") ? new QCalendarParticipantPK(forProperty("calParticipant")) : null;
    }

}


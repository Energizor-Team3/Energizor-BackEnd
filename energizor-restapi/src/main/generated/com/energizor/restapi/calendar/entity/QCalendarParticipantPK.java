package com.energizor.restapi.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendarParticipantPK is a Querydsl query type for CalendarParticipantPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCalendarParticipantPK extends BeanPath<CalendarParticipantPK> {

    private static final long serialVersionUID = -2072383689L;

    public static final QCalendarParticipantPK calendarParticipantPK = new QCalendarParticipantPK("calendarParticipantPK");

    public final NumberPath<Integer> calNo = createNumber("calNo", Integer.class);

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public QCalendarParticipantPK(String variable) {
        super(CalendarParticipantPK.class, forVariable(variable));
    }

    public QCalendarParticipantPK(Path<? extends CalendarParticipantPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendarParticipantPK(PathMetadata metadata) {
        super(CalendarParticipantPK.class, metadata);
    }

}


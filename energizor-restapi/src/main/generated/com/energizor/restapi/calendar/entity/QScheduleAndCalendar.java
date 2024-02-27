package com.energizor.restapi.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleAndCalendar is a Querydsl query type for ScheduleAndCalendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleAndCalendar extends EntityPathBase<ScheduleAndCalendar> {

    private static final long serialVersionUID = 691908165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleAndCalendar scheduleAndCalendar = new QScheduleAndCalendar("scheduleAndCalendar");

    public final QCalendar calendar;

    public final StringPath schAllDay = createString("schAllDay");

    public final StringPath schDetail = createString("schDetail");

    public final DateTimePath<java.time.LocalDateTime> schEndDate = createDateTime("schEndDate", java.time.LocalDateTime.class);

    public final StringPath schLocal = createString("schLocal");

    public final NumberPath<Integer> schNo = createNumber("schNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> schStartDate = createDateTime("schStartDate", java.time.LocalDateTime.class);

    public final StringPath schTitle = createString("schTitle");

    public QScheduleAndCalendar(String variable) {
        this(ScheduleAndCalendar.class, forVariable(variable), INITS);
    }

    public QScheduleAndCalendar(Path<? extends ScheduleAndCalendar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleAndCalendar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleAndCalendar(PathMetadata metadata, PathInits inits) {
        this(ScheduleAndCalendar.class, metadata, inits);
    }

    public QScheduleAndCalendar(Class<? extends ScheduleAndCalendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calendar = inits.isInitialized("calendar") ? new QCalendar(forProperty("calendar")) : null;
    }

}


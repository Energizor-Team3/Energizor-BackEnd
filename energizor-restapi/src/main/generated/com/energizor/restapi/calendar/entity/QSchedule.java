package com.energizor.restapi.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 979749328L;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final NumberPath<Integer> calNo = createNumber("calNo", Integer.class);

    public final StringPath schAllDay = createString("schAllDay");

    public final StringPath schDetail = createString("schDetail");

    public final DateTimePath<java.time.LocalDateTime> schEndDate = createDateTime("schEndDate", java.time.LocalDateTime.class);

    public final StringPath schLocal = createString("schLocal");

    public final NumberPath<Integer> schNo = createNumber("schNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> schStartDate = createDateTime("schStartDate", java.time.LocalDateTime.class);

    public final StringPath schTitle = createString("schTitle");

    public QSchedule(String variable) {
        super(Schedule.class, forVariable(variable));
    }

    public QSchedule(Path<? extends Schedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedule(PathMetadata metadata) {
        super(Schedule.class, metadata);
    }

}


package com.energizor.restapi.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMeetingTime is a Querydsl query type for MeetingTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeetingTime extends EntityPathBase<MeetingTime> {

    private static final long serialVersionUID = 226405629L;

    public static final QMeetingTime meetingTime = new QMeetingTime("meetingTime");

    public final NumberPath<Integer> meetTime = createNumber("meetTime", Integer.class);

    public final StringPath time = createString("time");

    public QMeetingTime(String variable) {
        super(MeetingTime.class, forVariable(variable));
    }

    public QMeetingTime(Path<? extends MeetingTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeetingTime(PathMetadata metadata) {
        super(MeetingTime.class, metadata);
    }

}


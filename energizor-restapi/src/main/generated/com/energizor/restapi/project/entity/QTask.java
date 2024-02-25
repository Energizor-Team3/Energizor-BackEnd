package com.energizor.restapi.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = 669943453L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTask task = new QTask("task");

    public final QProjectParticipant proParNo;

    public final StringPath taskContent = createString("taskContent");

    public final NumberPath<Integer> taskNo = createNumber("taskNo", Integer.class);

    public final StringPath taskStatus = createString("taskStatus");

    public QTask(String variable) {
        this(Task.class, forVariable(variable), INITS);
    }

    public QTask(Path<? extends Task> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTask(PathMetadata metadata, PathInits inits) {
        this(Task.class, metadata, inits);
    }

    public QTask(Class<? extends Task> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.proParNo = inits.isInitialized("proParNo") ? new QProjectParticipant(forProperty("proParNo"), inits.get("proParNo")) : null;
    }

}


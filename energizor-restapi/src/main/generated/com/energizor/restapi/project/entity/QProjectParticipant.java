package com.energizor.restapi.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectParticipant is a Querydsl query type for ProjectParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectParticipant extends EntityPathBase<ProjectParticipant> {

    private static final long serialVersionUID = 1951144306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectParticipant projectParticipant = new QProjectParticipant("projectParticipant");

    public final NumberPath<Integer> proNo = createNumber("proNo", Integer.class);

    public final NumberPath<Integer> proParNo = createNumber("proParNo", Integer.class);

    public final com.energizor.restapi.users.entity.QUser user;

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public QProjectParticipant(String variable) {
        this(ProjectParticipant.class, forVariable(variable), INITS);
    }

    public QProjectParticipant(Path<? extends ProjectParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectParticipant(PathMetadata metadata, PathInits inits) {
        this(ProjectParticipant.class, metadata, inits);
    }

    public QProjectParticipant(Class<? extends ProjectParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


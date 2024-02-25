package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersGroup is a Querydsl query type for UsersGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersGroup extends EntityPathBase<UsersGroup> {

    private static final long serialVersionUID = -1285195051L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersGroup usersGroup = new QUsersGroup("usersGroup");

    public final QTeamGroup teamGroup;

    public final NumberPath<Long> userCode = createNumber("userCode", Long.class);

    public final StringPath userName = createString("userName");

    public QUsersGroup(String variable) {
        this(UsersGroup.class, forVariable(variable), INITS);
    }

    public QUsersGroup(Path<? extends UsersGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersGroup(PathMetadata metadata, PathInits inits) {
        this(UsersGroup.class, metadata, inits);
    }

    public QUsersGroup(Class<? extends UsersGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teamGroup = inits.isInitialized("teamGroup") ? new QTeamGroup(forProperty("teamGroup")) : null;
    }

}


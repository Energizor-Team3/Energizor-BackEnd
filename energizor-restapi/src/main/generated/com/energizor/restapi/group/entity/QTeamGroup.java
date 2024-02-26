package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamGroup is a Querydsl query type for TeamGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamGroup extends EntityPathBase<TeamGroup> {

    private static final long serialVersionUID = -1150250780L;

    public static final QTeamGroup teamGroup = new QTeamGroup("teamGroup");

    public final NumberPath<Long> deptCode = createNumber("deptCode", Long.class);

    public final NumberPath<Long> teamCode = createNumber("teamCode", Long.class);

    public final StringPath teamName = createString("teamName");

    public final ListPath<UsersGroup, QUsersGroup> userList = this.<UsersGroup, QUsersGroup>createList("userList", UsersGroup.class, QUsersGroup.class, PathInits.DIRECT2);

    public QTeamGroup(String variable) {
        super(TeamGroup.class, forVariable(variable));
    }

    public QTeamGroup(Path<? extends TeamGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeamGroup(PathMetadata metadata) {
        super(TeamGroup.class, metadata);
    }

}


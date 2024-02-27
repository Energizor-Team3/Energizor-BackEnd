package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamAndUsers is a Querydsl query type for TeamAndUsers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamAndUsers extends EntityPathBase<TeamAndUsers> {

    private static final long serialVersionUID = 956809708L;

    public static final QTeamAndUsers teamAndUsers = new QTeamAndUsers("teamAndUsers");

    public final NumberPath<Long> teamCode = createNumber("teamCode", Long.class);

    public final StringPath teamName = createString("teamName");

    public final ListPath<UsersGroup, QUsersGroup> userlist = this.<UsersGroup, QUsersGroup>createList("userlist", UsersGroup.class, QUsersGroup.class, PathInits.DIRECT2);

    public QTeamAndUsers(String variable) {
        super(TeamAndUsers.class, forVariable(variable));
    }

    public QTeamAndUsers(Path<? extends TeamAndUsers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeamAndUsers(PathMetadata metadata) {
        super(TeamAndUsers.class, metadata);
    }

}


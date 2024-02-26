package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeptAndTeam is a Querydsl query type for DeptAndTeam
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeptAndTeam extends EntityPathBase<DeptAndTeam> {

    private static final long serialVersionUID = -1785949935L;

    public static final QDeptAndTeam deptAndTeam = new QDeptAndTeam("deptAndTeam");

    public final NumberPath<Long> deptCode = createNumber("deptCode", Long.class);

    public final StringPath deptName = createString("deptName");

    public final ListPath<TeamGroup, QTeamGroup> teamList = this.<TeamGroup, QTeamGroup>createList("teamList", TeamGroup.class, QTeamGroup.class, PathInits.DIRECT2);

    public QDeptAndTeam(String variable) {
        super(DeptAndTeam.class, forVariable(variable));
    }

    public QDeptAndTeam(Path<? extends DeptAndTeam> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeptAndTeam(PathMetadata metadata) {
        super(DeptAndTeam.class, metadata);
    }

}


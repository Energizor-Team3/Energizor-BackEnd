package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAllGroupList is a Querydsl query type for AllGroupList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAllGroupList extends EntityPathBase<AllGroupList> {

    private static final long serialVersionUID = 1803928858L;

    public static final QAllGroupList allGroupList = new QAllGroupList("allGroupList");

    public final NumberPath<Long> deptCode = createNumber("deptCode", Long.class);

    public final StringPath deptName = createString("deptName");

    public final ListPath<TeamGroup, QTeamGroup> teamList = this.<TeamGroup, QTeamGroup>createList("teamList", TeamGroup.class, QTeamGroup.class, PathInits.DIRECT2);

    public QAllGroupList(String variable) {
        super(AllGroupList.class, forVariable(variable));
    }

    public QAllGroupList(Path<? extends AllGroupList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAllGroupList(PathMetadata metadata) {
        super(AllGroupList.class, metadata);
    }

}


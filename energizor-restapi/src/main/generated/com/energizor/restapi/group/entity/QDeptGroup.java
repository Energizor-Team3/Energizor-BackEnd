package com.energizor.restapi.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeptGroup is a Querydsl query type for DeptGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeptGroup extends EntityPathBase<DeptGroup> {

    private static final long serialVersionUID = -1667693060L;

    public static final QDeptGroup deptGroup = new QDeptGroup("deptGroup");

    public final NumberPath<Long> deptCode = createNumber("deptCode", Long.class);

    public final StringPath deptName = createString("deptName");

    public QDeptGroup(String variable) {
        super(DeptGroup.class, forVariable(variable));
    }

    public QDeptGroup(Path<? extends DeptGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeptGroup(PathMetadata metadata) {
        super(DeptGroup.class, metadata);
    }

}


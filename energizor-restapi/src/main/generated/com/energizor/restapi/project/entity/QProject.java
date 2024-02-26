package com.energizor.restapi.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 800408321L;

    public static final QProject project = new QProject("project");

    public final StringPath proContent = createString("proContent");

    public final DatePath<java.sql.Date> proEndDate = createDate("proEndDate", java.sql.Date.class);

    public final NumberPath<Integer> proNo = createNumber("proNo", Integer.class);

    public final DatePath<java.sql.Date> proStartDate = createDate("proStartDate", java.sql.Date.class);

    public final StringPath proStatus = createString("proStatus");

    public final StringPath proTitle = createString("proTitle");

    public QProject(String variable) {
        super(Project.class, forVariable(variable));
    }

    public QProject(Path<? extends Project> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProject(PathMetadata metadata) {
        super(Project.class, metadata);
    }

}


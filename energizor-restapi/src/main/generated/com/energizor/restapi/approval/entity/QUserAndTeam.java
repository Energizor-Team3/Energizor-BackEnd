package com.energizor.restapi.approval.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAndTeam is a Querydsl query type for UserAndTeam
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAndTeam extends EntityPathBase<UserAndTeam> {

    private static final long serialVersionUID = 1393853835L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAndTeam userAndTeam = new QUserAndTeam("userAndTeam");

    public final QDayOff dayOff;

    public final StringPath email = createString("email");

    public final StringPath imgName = createString("imgName");

    public final StringPath phone = createString("phone");

    public final com.energizor.restapi.users.entity.QTeam team;

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final StringPath userRank = createString("userRank");

    public QUserAndTeam(String variable) {
        this(UserAndTeam.class, forVariable(variable), INITS);
    }

    public QUserAndTeam(Path<? extends UserAndTeam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAndTeam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAndTeam(PathMetadata metadata, PathInits inits) {
        this(UserAndTeam.class, metadata, inits);
    }

    public QUserAndTeam(Class<? extends UserAndTeam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dayOff = inits.isInitialized("dayOff") ? new QDayOff(forProperty("dayOff"), inits.get("dayOff")) : null;
        this.team = inits.isInitialized("team") ? new com.energizor.restapi.users.entity.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}


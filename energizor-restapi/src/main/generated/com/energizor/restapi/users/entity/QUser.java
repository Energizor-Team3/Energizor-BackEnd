package com.energizor.restapi.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1625453010L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QDayoff dayoff;

    public final StringPath email = createString("email");

    public final DatePath<java.util.Date> entDate = createDate("entDate", java.util.Date.class);

    public final StringPath phone = createString("phone");

    public final StringPath profilePath = createString("profilePath");

    public final DatePath<java.util.Date> resignDate = createDate("resignDate", java.util.Date.class);

    public final QTeam team;

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final StringPath userPw = createString("userPw");

    public final StringPath userRank = createString("userRank");

    public final ListPath<UserRole, QUserRole> userRole = this.<UserRole, QUserRole>createList("userRole", UserRole.class, QUserRole.class, PathInits.DIRECT2);

    public final StringPath userStatus = createString("userStatus");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dayoff = inits.isInitialized("dayoff") ? new QDayoff(forProperty("dayoff"), inits.get("dayoff")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
    }

}


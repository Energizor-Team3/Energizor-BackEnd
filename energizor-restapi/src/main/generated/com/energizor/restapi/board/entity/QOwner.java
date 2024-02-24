package com.energizor.restapi.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOwner is a Querydsl query type for Owner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOwner extends EntityPathBase<Owner> {

    private static final long serialVersionUID = 525837678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOwner owner = new QOwner("owner");

    public final QDayOff dayOff;

    public final DateTimePath<java.time.LocalDateTime> entDate = createDateTime("entDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> ownerCode = createNumber("ownerCode", Integer.class);

    public final StringPath ownerEmail = createString("ownerEmail");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerPhone = createString("ownerPhone");

    public final StringPath ownerPw = createString("ownerPw");

    public final StringPath ownerRank = createString("ownerRank");

    public final DateTimePath<java.time.LocalDateTime> resingDate = createDateTime("resingDate", java.time.LocalDateTime.class);

    public final QTeam team;

    public final StringPath userStatus = createString("userStatus");

    public QOwner(String variable) {
        this(Owner.class, forVariable(variable), INITS);
    }

    public QOwner(Path<? extends Owner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOwner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOwner(PathMetadata metadata, PathInits inits) {
        this(Owner.class, metadata, inits);
    }

    public QOwner(Class<? extends Owner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dayOff = inits.isInitialized("dayOff") ? new QDayOff(forProperty("dayOff"), inits.get("dayOff")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
    }

}


package com.energizor.restapi.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestBoard is a Querydsl query type for InterestBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestBoard extends EntityPathBase<InterestBoard> {

    private static final long serialVersionUID = -748615273L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestBoard interestBoard = new QInterestBoard("interestBoard");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final NumberPath<Integer> interestCode = createNumber("interestCode", Integer.class);

    public final com.energizor.restapi.users.entity.QUser owner;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final com.energizor.restapi.users.entity.QUser user;

    public QInterestBoard(String variable) {
        this(InterestBoard.class, forVariable(variable), INITS);
    }

    public QInterestBoard(Path<? extends InterestBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestBoard(PathMetadata metadata, PathInits inits) {
        this(InterestBoard.class, metadata, inits);
    }

    public QInterestBoard(Class<? extends InterestBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.owner = inits.isInitialized("owner") ? new com.energizor.restapi.users.entity.QUser(forProperty("owner"), inits.get("owner")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


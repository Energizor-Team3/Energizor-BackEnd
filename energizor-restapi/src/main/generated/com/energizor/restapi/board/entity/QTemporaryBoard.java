package com.energizor.restapi.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTemporaryBoard is a Querydsl query type for TemporaryBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemporaryBoard extends EntityPathBase<TemporaryBoard> {

    private static final long serialVersionUID = 1762065082L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTemporaryBoard temporaryBoard = new QTemporaryBoard("temporaryBoard");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoardType boardType;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final NumberPath<Integer> temporaryCode = createNumber("temporaryCode", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final com.energizor.restapi.users.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QTemporaryBoard(String variable) {
        this(TemporaryBoard.class, forVariable(variable), INITS);
    }

    public QTemporaryBoard(Path<? extends TemporaryBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTemporaryBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTemporaryBoard(PathMetadata metadata, PathInits inits) {
        this(TemporaryBoard.class, metadata, inits);
    }

    public QTemporaryBoard(Class<? extends TemporaryBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardType = inits.isInitialized("boardType") ? new QBoardType(forProperty("boardType")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.energizor.restapi.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 513581473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> boardCode = createNumber("boardCode", Integer.class);

    public final QBoardType boardType;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    public final ListPath<InterestBoard, QInterestBoard> interestBoards = this.<InterestBoard, QInterestBoard>createList("interestBoards", InterestBoard.class, QInterestBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final BooleanPath temporaryOpt = createBoolean("temporaryOpt");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final com.energizor.restapi.users.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardType = inits.isInitialized("boardType") ? new QBoardType(forProperty("boardType")) : null;
        this.user = inits.isInitialized("user") ? new com.energizor.restapi.users.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


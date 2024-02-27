package com.energizor.restapi.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSendNote is a Querydsl query type for SendNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSendNote extends EntityPathBase<SendNote> {

    private static final long serialVersionUID = 687911559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSendNote sendNote = new QSendNote("sendNote");

    public final StringPath noteContent = createString("noteContent");

    public final DateTimePath<java.util.Date> noteSendDate = createDateTime("noteSendDate", java.util.Date.class);

    public final StringPath noteTitle = createString("noteTitle");

    public final ListPath<com.energizor.restapi.group.entity.UsersGroup, com.energizor.restapi.group.entity.QUsersGroup> renUserCode = this.<com.energizor.restapi.group.entity.UsersGroup, com.energizor.restapi.group.entity.QUsersGroup>createList("renUserCode", com.energizor.restapi.group.entity.UsersGroup.class, com.energizor.restapi.group.entity.QUsersGroup.class, PathInits.DIRECT2);

    public final NumberPath<Integer> sendNoteCode = createNumber("sendNoteCode", Integer.class);

    public final com.energizor.restapi.group.entity.QUsersGroup sendUserCode;

    public QSendNote(String variable) {
        this(SendNote.class, forVariable(variable), INITS);
    }

    public QSendNote(Path<? extends SendNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSendNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSendNote(PathMetadata metadata, PathInits inits) {
        this(SendNote.class, metadata, inits);
    }

    public QSendNote(Class<? extends SendNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sendUserCode = inits.isInitialized("sendUserCode") ? new com.energizor.restapi.group.entity.QUsersGroup(forProperty("sendUserCode"), inits.get("sendUserCode")) : null;
    }

}


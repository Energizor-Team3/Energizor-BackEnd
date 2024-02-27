package com.energizor.restapi.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSendNoteNotYet is a Querydsl query type for SendNoteNotYet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSendNoteNotYet extends EntityPathBase<SendNoteNotYet> {

    private static final long serialVersionUID = -284641156L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSendNoteNotYet sendNoteNotYet = new QSendNoteNotYet("sendNoteNotYet");

    public final NumberPath<Integer> copyOfNoteCode = createNumber("copyOfNoteCode", Integer.class);

    public final StringPath noteContent = createString("noteContent");

    public final DateTimePath<java.util.Date> noteSendDate = createDateTime("noteSendDate", java.util.Date.class);

    public final StringPath noteTitle = createString("noteTitle");

    public final ListPath<com.energizor.restapi.group.entity.UsersGroup, com.energizor.restapi.group.entity.QUsersGroup> renUserCode = this.<com.energizor.restapi.group.entity.UsersGroup, com.energizor.restapi.group.entity.QUsersGroup>createList("renUserCode", com.energizor.restapi.group.entity.UsersGroup.class, com.energizor.restapi.group.entity.QUsersGroup.class, PathInits.DIRECT2);

    public final com.energizor.restapi.group.entity.QUsersGroup sendUserCode;

    public QSendNoteNotYet(String variable) {
        this(SendNoteNotYet.class, forVariable(variable), INITS);
    }

    public QSendNoteNotYet(Path<? extends SendNoteNotYet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSendNoteNotYet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSendNoteNotYet(PathMetadata metadata, PathInits inits) {
        this(SendNoteNotYet.class, metadata, inits);
    }

    public QSendNoteNotYet(Class<? extends SendNoteNotYet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sendUserCode = inits.isInitialized("sendUserCode") ? new com.energizor.restapi.group.entity.QUsersGroup(forProperty("sendUserCode"), inits.get("sendUserCode")) : null;
    }

}


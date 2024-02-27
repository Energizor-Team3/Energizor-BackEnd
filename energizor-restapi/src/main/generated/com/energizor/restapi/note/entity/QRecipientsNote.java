package com.energizor.restapi.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipientsNote is a Querydsl query type for RecipientsNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipientsNote extends EntityPathBase<RecipientsNote> {

    private static final long serialVersionUID = -1609775687L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipientsNote recipientsNote = new QRecipientsNote("recipientsNote");

    public final DateTimePath<java.util.Date> renReadingDate = createDateTime("renReadingDate", java.util.Date.class);

    public final StringPath renReadingState = createString("renReadingState");

    public final StringPath renSaveState = createString("renSaveState");

    public final com.energizor.restapi.users.entity.QUser renUserCode;

    public final com.energizor.restapi.users.entity.QUser senNoteCode;

    public QRecipientsNote(String variable) {
        this(RecipientsNote.class, forVariable(variable), INITS);
    }

    public QRecipientsNote(Path<? extends RecipientsNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipientsNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipientsNote(PathMetadata metadata, PathInits inits) {
        this(RecipientsNote.class, metadata, inits);
    }

    public QRecipientsNote(Class<? extends RecipientsNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.renUserCode = inits.isInitialized("renUserCode") ? new com.energizor.restapi.users.entity.QUser(forProperty("renUserCode"), inits.get("renUserCode")) : null;
        this.senNoteCode = inits.isInitialized("senNoteCode") ? new com.energizor.restapi.users.entity.QUser(forProperty("senNoteCode"), inits.get("senNoteCode")) : null;
    }

}


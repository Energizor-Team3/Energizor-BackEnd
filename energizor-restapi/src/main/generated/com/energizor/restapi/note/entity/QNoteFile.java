package com.energizor.restapi.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoteFile is a Querydsl query type for NoteFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoteFile extends EntityPathBase<NoteFile> {

    private static final long serialVersionUID = 1021350779L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoteFile noteFile = new QNoteFile("noteFile");

    public final NumberPath<Integer> noteFileCode = createNumber("noteFileCode", Integer.class);

    public final StringPath noteOriginalFileName = createString("noteOriginalFileName");

    public final StringPath noteOriginalPath = createString("noteOriginalPath");

    public final StringPath noteSaveFileName = createString("noteSaveFileName");

    public final StringPath noteSavePath = createString("noteSavePath");

    public final QSendNote sendNoteCode;

    public QNoteFile(String variable) {
        this(NoteFile.class, forVariable(variable), INITS);
    }

    public QNoteFile(Path<? extends NoteFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoteFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoteFile(PathMetadata metadata, PathInits inits) {
        this(NoteFile.class, metadata, inits);
    }

    public QNoteFile(Class<? extends NoteFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sendNoteCode = inits.isInitialized("sendNoteCode") ? new QSendNote(forProperty("sendNoteCode"), inits.get("sendNoteCode")) : null;
    }

}


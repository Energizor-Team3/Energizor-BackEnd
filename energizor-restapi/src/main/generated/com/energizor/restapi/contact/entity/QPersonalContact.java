package com.energizor.restapi.contact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonalContact is a Querydsl query type for PersonalContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonalContact extends EntityPathBase<PersonalContact> {

    private static final long serialVersionUID = 1052544193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonalContact personalContact = new QPersonalContact("personalContact");

    public final NumberPath<Integer> pcCode = createNumber("pcCode", Integer.class);

    public final StringPath pcCompany = createString("pcCompany");

    public final StringPath pcDept = createString("pcDept");

    public final StringPath pcEmail = createString("pcEmail");

    public final StringPath pcName = createString("pcName");

    public final StringPath pcPhone = createString("pcPhone");

    public final StringPath pcRank = createString("pcRank");

    public final QUser user;

    public QPersonalContact(String variable) {
        this(PersonalContact.class, forVariable(variable), INITS);
    }

    public QPersonalContact(Path<? extends PersonalContact> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonalContact(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonalContact(PathMetadata metadata, PathInits inits) {
        this(PersonalContact.class, metadata, inits);
    }

    public QPersonalContact(Class<? extends PersonalContact> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


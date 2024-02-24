package com.energizor.restapi.contact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanyContact is a Querydsl query type for CompanyContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyContact extends EntityPathBase<CompanyContact> {

    private static final long serialVersionUID = 964756930L;

    public static final QCompanyContact companyContact = new QCompanyContact("companyContact");

    public final NumberPath<Integer> cpCode = createNumber("cpCode", Integer.class);

    public final NumberPath<Integer> userCode = createNumber("userCode", Integer.class);

    public QCompanyContact(String variable) {
        super(CompanyContact.class, forVariable(variable));
    }

    public QCompanyContact(Path<? extends CompanyContact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyContact(PathMetadata metadata) {
        super(CompanyContact.class, metadata);
    }

}


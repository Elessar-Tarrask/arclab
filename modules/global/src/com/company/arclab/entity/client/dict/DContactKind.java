package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "JCRM_D_CONTACT_KIND")
@Entity(name = "jcrm_DContactKind")
@NamePattern("%s|name")
public class DContactKind extends BaseStringIdEntity {
    private static final long serialVersionUID = -505876044248424091L;

    public static final String KIND_ID_MOBILE = "MOBILE";

    public static final String KIND_ID_HOUSE = "HOUSE";

    public static final String KIND_ID_WORK = "WORK";

    public static final String KIND_ID_EMAILSTMT = "EMAILSTMT";

    @Id
    @Column(name = "ID", nullable = false, length = 10)
    protected String id;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
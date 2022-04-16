package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "JCRM_D_TYPE_EXT")
@Entity(name = "jcrm_DTypeExt")
public class DTypeExt extends BaseStringIdEntity {
    private static final long serialVersionUID = -2941932332882265181L;

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
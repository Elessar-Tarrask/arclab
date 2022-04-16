package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "JCRM_D_LICENSE")
@Entity(name = "jcrm_DLicense")
@NamePattern("%s|name")
public class DLicense extends StandardEntity {
    private static final long serialVersionUID = 4526611471097782552L;

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
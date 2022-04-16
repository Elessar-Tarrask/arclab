package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "JCRM_D_REG_DOC_TYPE")
@Entity(name = "jcrm_DRegDocType")
@NamePattern("%s|name")
public class DRegDocType extends StandardEntity {
    private static final long serialVersionUID = -4059241834983527969L;

    public static final String Z_GRUL = "Z_GRUL"; // Справка о государственной регистрации юридического лица

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
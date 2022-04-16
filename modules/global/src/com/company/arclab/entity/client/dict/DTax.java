package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "JCRM_D_TAX")
@Entity(name = "jcrm_DTax")
@NamePattern("%s|name")
public class DTax extends StandardEntity {
    private static final long serialVersionUID = 5017378475289675090L;

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLVIR_ID")
    private String colvirId;

    public String getColvirId() {
        return colvirId;
    }

    public void setColvirId(String colvirId) {
        this.colvirId = colvirId;
    }

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
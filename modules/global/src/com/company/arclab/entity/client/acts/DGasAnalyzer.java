package com.company.arclab.entity.client.acts;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "ARCLAB_D_GAS_ANALYZER")
@Entity(name = "arclab_DGasAnalyzer")
@NamePattern("%s|name")
public class DGasAnalyzer extends StandardEntity {
    private static final long serialVersionUID = 7135158166915114981L;

    @Column(name = "CODE", nullable = false)
    @NotNull
    private String code;

    @Column(name = "NAME", nullable = false)
    @NotNull
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
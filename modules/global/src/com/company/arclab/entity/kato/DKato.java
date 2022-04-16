package com.company.arclab.entity.kato;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "JCRM_D_KATO")
@Entity(name = "jcrm_DKato")
@NamePattern("%s|katoName")
public class DKato extends StandardEntity {
    private static final long serialVersionUID = -1841602327235478835L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private DKato parent;

    @Column(name = "KATO_CODE", nullable = false, unique = true)
    @NotNull
    private Long katoCode;

    @Column(name = "KATO_NAME")
    private String katoName;

    public DKato getParent() {
        return parent;
    }

    public void setParent(DKato parent) {
        this.parent = parent;
    }

    public String getKatoName() {
        return katoName;
    }

    public void setKatoName(String katoName) {
        this.katoName = katoName;
    }

    public Long getKatoCode() {
        return katoCode;
    }

    public void setKatoCode(Long katoCode) {
        this.katoCode = katoCode;
    }

}
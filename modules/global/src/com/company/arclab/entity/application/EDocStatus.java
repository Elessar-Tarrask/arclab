package com.company.arclab.entity.application;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EDocStatus implements EnumClass<String> {

    ACTIVE("ACTIVE"),
    ARCHIVE("ARCHIVE");

    private String id;

    EDocStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EDocStatus fromId(String id) {
        for (EDocStatus at : EDocStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
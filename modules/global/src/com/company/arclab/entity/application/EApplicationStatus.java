package com.company.arclab.entity.application;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EApplicationStatus implements EnumClass<String> {

    NEW("NEW"),
    CONSIDERING("CONSIDERING"),
    CANCELED("CANCELED"),
    REJECTED("REJECTED"),
    FINISHED("FINISHED"),
    ERROR("ERROR"),
    CREDIT_COMMITTEE("CREDIT_COMMITTEE"),
    EXPIRED("EXPIRED"),
    CANCELLED_BY_SYSTEM("CANCELLED_BY_SYSTEM");

    private String id;

    EApplicationStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EApplicationStatus fromId(String id) {
        for (EApplicationStatus at : EApplicationStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
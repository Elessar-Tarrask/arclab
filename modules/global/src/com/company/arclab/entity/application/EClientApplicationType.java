package com.company.arclab.entity.application;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EClientApplicationType implements EnumClass<String> {

    CREATE("CREATE"),
    MODIFY("MODIFY");

    private String id;

    EClientApplicationType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EClientApplicationType fromId(String id) {
        for (EClientApplicationType at : EClientApplicationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
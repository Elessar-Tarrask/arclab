package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ESex implements EnumClass<String> {

    MALE("Male"),
    FEMALE("Female");

    private String id;

    ESex(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ESex fromId(String id) {
        for (ESex at : ESex.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
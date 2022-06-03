package com.company.arclab.entity.client.acts;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum SampleTakePlace implements EnumClass<String> {

    LABORATORY("В лаборатории"),
    SAMPLE_POINT("В точке отбора");

    private String id;

    SampleTakePlace(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SampleTakePlace fromId(String id) {
        for (SampleTakePlace at : SampleTakePlace.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
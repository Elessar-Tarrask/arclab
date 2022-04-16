package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EClientType implements EnumClass<String> {

    ENTITY("Юридическое лицо"),
//    ENTREPRENEUR("Индивидуальный предприниматель"), // nsavchenko, 2020/12/28 - удалил потому что не используется. Заменил на ENTREPRENEUR_INDIVIDUAL для депозитов при импорте ставок
    ENTREPRENEUR_INDIVIDUAL("Индивидуальный предприниматель частное"),
    ENTREPRENEUR_JOINT("Индивидуальный предприниматель совместное"),
    INDIVIDUAL("Физическое лицо");

    private String id;

    EClientType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EClientType fromId(String id) {
        for (EClientType at : EClientType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
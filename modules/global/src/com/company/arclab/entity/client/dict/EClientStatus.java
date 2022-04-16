package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EClientStatus implements EnumClass<String> {

    ACTIVE("Active"),
    POTENTIAL("Potential"),
    INACTIVE("Inactive"),
    NEW("New"), // для клиентов создаваемых заявкой через CRM
    CLOSED("Closed"),
    MODIFY("Modify"); // временная копия клиента для процесса изменения клиента //https://jira.almanit.kz/browse/JCRM-3265

    private String id;

    EClientStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EClientStatus fromId(String id) {
        for (EClientStatus at : EClientStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
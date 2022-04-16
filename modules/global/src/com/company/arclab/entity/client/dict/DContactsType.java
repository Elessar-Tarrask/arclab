package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "JCRM_D_CONTACTS_TYPE")
@Entity(name = "jcrm_DContactsType")
@NamePattern("%s|name")
public class DContactsType extends BaseStringIdEntity {
    private static final long serialVersionUID = -7511413373848915812L;

    public static final String TYPE_ID_EML_IB = "EML_IB"; // E-mail для СИБ
    public static final String TYPE_ID_EML = "EML"; // E-mail
    public static final String TYPE_ID_MOB_IB = "MOB_IB"; // Мобильный телефон для СИБ
    public static final String TYPE_ID_MOB = "MOB"; // Мобильный телефон
    public static final String TYPE_ID_PHN = "PHN"; // Телефон


    @Id
    @Column(name = "ID", nullable = false, length = 10)
    protected String id;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}




package com.company.arclab.entity.client.dict;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "JCRM_D_ID_DOC")
@Entity(name = "jcrm_DIdDoc")
public class DIdDoc extends StandardEntity {
    private static final long serialVersionUID = 2325616907551964818L;

    public static final String ID_DOC_CODE_UDOS = "002";                       // Удостоверение личности
    public static final String ID_DOC_CODE_RESIDENCE = "005";                 // Вид на жительство
    public static final String ID_DOC_Foreign_passport = "03";                // Паспорт гражданина иностранного государства
    public static final String ID_DOC_Kazakh_passport = "001";                // Паспорт гражданина

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    protected String code;

    @Column(name = "NAME")
    protected String name;

//    @Lob
//    @Column(name = "DESCRIPTION")
//    protected String description;

//    public String getDescription() {
//        return description;
//    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

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
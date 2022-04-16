package com.company.arclab.entity.kato;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "JCRM_D_ADDRESS_TYPE")
@Entity(name = "jcrm_DAddressType")
public class DAddressType extends StandardEntity {
    private static final long serialVersionUID = 5224966300240223959L;

    public static final String ADDRESS_TYPE_CODE_OTHER = "000"; // Иной

    public static final String ADDRESS_TYPE_CODE_BIRTH = "001"; // Место рождения

    public static final String ADDRESS_TYPE_CODE_REG = "002"; // Место регистрации

    public static final String ADDRESS_TYPE_CODE_LIVE = "003"; // Место жительства

    public static final String ADDRESS_TYPE_CODE_WORK = "004"; // Место работы

    public static final String ADDRESS_TYPE_CODE_LEGAL = "005"; // юридический

    public static final String ADDRESS_TYPE_CODE_ACTUAL = "006"; // фактический

    public static final String ADDRESS_TYPE_CODE_MAIL = "007"; // Для корреспонденций

    public static final String ADDRESS_TYPE_CODE_ADMIN_ORG = "008"; // Административных органов

    public static final String ADDRESS_TYPE_CODE_LICENSE_ORG = "009"; // Лицензирующих органов

    public static final String ADDRESS_TYPE_CODE_REG_ORG = "010"; // Регистрирующих органов

    public static final String ADDRESS_TYPE_CODE_TEMP = "011"; // Адрес временной регистрации

    public static final String ADDRESS_TYPE_CODE_ENGLISH = "012"; // Адрес на английском языке

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    protected String code;

    @Column(name = "NAME")
    protected String name;

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
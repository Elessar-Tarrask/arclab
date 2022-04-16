package com.company.arclab.entity.kato;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "JCRM_KATO_COUNTRY")
@Entity(name = "jcrm_Country")
@NamePattern("%s %s|countryName,code")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -2417109553406662671L;

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    protected String code;

    @Column(name = "CODE_WAY4", unique = true)
    private String codeWay4;

    @Column(name = "COUNTRY_NAME")
    protected String countryName;

    public String getCodeWay4() {
        return codeWay4;
    }

    public void setCodeWay4(String codeWay4) {
        this.codeWay4 = codeWay4;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
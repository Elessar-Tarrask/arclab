package com.company.arclab.entity.kato;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|cityName")
@Table(name = "JCRM_KATO_CITY")
@Entity(name = "jcrm_City")
public class City extends StandardEntity {
    private static final long serialVersionUID = -7634030591962843543L;

    @Column(name = "CITY_NAME")
    protected String cityName;

    @Column(name = "CODE")
    protected String code;

    @Column(name = "VALUE_")
    protected String value;

    @Column(name = "DESCRIPTION")
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
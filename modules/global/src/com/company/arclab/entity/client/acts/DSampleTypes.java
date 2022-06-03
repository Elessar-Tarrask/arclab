package com.company.arclab.entity.client.acts;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "ARCLAB_D_SAMPLE_TYPES")
@Entity(name = "arclab_DSampleTypes")
public class DSampleTypes extends StandardEntity {
    private static final long serialVersionUID = 9052035320380799249L;

    @Column(name = "TYPE_", nullable = false)
    @NotNull
    private String type;

    @Column(name = "CODE", nullable = false)
    @NotNull
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_WEATHER_CONDITIONS_ID")
    private TWeatherConditions tWeatherConditions;

    public TWeatherConditions gettWeatherConditions() {
        return tWeatherConditions;
    }

    public void settWeatherConditions(TWeatherConditions tWeatherConditions) {
        this.tWeatherConditions = tWeatherConditions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
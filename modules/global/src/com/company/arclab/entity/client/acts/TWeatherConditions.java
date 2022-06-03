package com.company.arclab.entity.client.acts;

import com.company.arclab.entity.application.EApplicationStatus;
import com.company.arclab.entity.client.Identity;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "ARCLAB_T_WEATHER_CONDITIONS")
@Entity(name = "arclab_TWeatherConditions")
public class TWeatherConditions extends StandardEntity {
    private static final long serialVersionUID = -2815443321546144727L;

    @Column(name = "BAROMETRIC_PRESSURE")
    private String barometricPressure;

    @Column(name = "AIR_HUMIDITY")
    private String airHumidity;

    @Column(name = "WIND_DIRECTION")
    private String windDirection;

    @Column(name = "WIND_VELOCITY")
    private String windVelocity;

    @Column(name = "AIR_TEMPERATURE")
    private String airTemperature;

    @Column(name = "RAINFALL")
    private String rainfall;

    @OneToMany(mappedBy = "tWeatherConditions")
    private List<DSampleTypes> sampleType;

    @Column(name = "VERIFICATION_DETAILS")
    private String verificationDetails;

    @Column(name = "TERRAIN_FEATURE", length = 10000)
    private String terrainFeature;

    @Column(name = "SAMPLE_TAKEN_FROM")
    private String sampleTakenFrom;

    @NotNull
    @Column(name = "TAKEN_DATE", nullable = false)
    private LocalDateTime takenDate;

    @Column(name = "EXPIRATORY_DATE")
    private LocalDateTime expiratoryDate;

    @Column(name = "MEASURE_STATUS")
    private String measureStatus;

    @Column(name = "DETERMINED_INGREDIENTS")
    private String determinedIngredients;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public EApplicationStatus getMeasureStatus() {
        return measureStatus == null ? null : EApplicationStatus.fromId(measureStatus);
    }

    public void setMeasureStatus(EApplicationStatus measureStatus) {
        this.measureStatus = measureStatus == null ? null : measureStatus.getId();
    }

    public LocalDateTime getExpiratoryDate() {
        return expiratoryDate;
    }

    public void setExpiratoryDate(LocalDateTime expiratoryDate) {
        this.expiratoryDate = expiratoryDate;
    }

    public LocalDateTime getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(LocalDateTime takenDate) {
        this.takenDate = takenDate;
    }

    public void setSampleTakenFrom(SampleTakePlace sampleTakenFrom) {
        this.sampleTakenFrom = sampleTakenFrom == null ? null : sampleTakenFrom.getId();
    }

    public SampleTakePlace getSampleTakenFrom() {
        return sampleTakenFrom == null ? null : SampleTakePlace.fromId(sampleTakenFrom);
    }

    public String getTerrainFeature() {
        return terrainFeature;
    }

    public void setTerrainFeature(String terrainFeature) {
        this.terrainFeature = terrainFeature;
    }

    public String getVerificationDetails() {
        return verificationDetails;
    }

    public void setVerificationDetails(String verificationDetails) {
        this.verificationDetails = verificationDetails;
    }

    public void setSampleType(List<DSampleTypes> sampleType) {
        this.sampleType = sampleType;
    }

    public List<DSampleTypes> getSampleType() {
        return sampleType;
    }

    public String getDeterminedIngredients() {
        return determinedIngredients;
    }

    public void setDeterminedIngredients(String determinedIngredients) {
        this.determinedIngredients = determinedIngredients;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(String airTemperature) {
        this.airTemperature = airTemperature;
    }

    public String getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(String windVelocity) {
        this.windVelocity = windVelocity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getBarometricPressure() {
        return barometricPressure;
    }

    public void setBarometricPressure(String barometricPressure) {
        this.barometricPressure = barometricPressure;
    }
}
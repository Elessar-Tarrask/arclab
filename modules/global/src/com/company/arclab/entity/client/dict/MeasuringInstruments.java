package com.company.arclab.entity.client.dict;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "ARCLAB_MEASURING_INSTRUMENTS")
@Entity(name = "arclab_MeasuringInstruments")
public class MeasuringInstruments extends StandardEntity {
    private static final long serialVersionUID = 1085717367032178469L;

    @Column(name = "CHARACTERISTIC_NAME")
    private String characteristicName;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "BASIC_METROLOGICAL")
    private String basicMetrological;

    @Column(name = "MANUFACTURE_YEAR")
    private LocalDate manufactureYear;

    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;

    @Column(name = "CERTIFICATE_DATE")
    private LocalDate certificateDate;

    @Column(name = "STATE_SYSTEM_NUMBER")
    private String stateSystemNumber;

    public String getStateSystemNumber() {
        return stateSystemNumber;
    }

    public void setStateSystemNumber(String stateSystemNumber) {
        this.stateSystemNumber = stateSystemNumber;
    }

    public LocalDate getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(LocalDate certificateDate) {
        this.certificateDate = certificateDate;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(LocalDate manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getBasicMetrological() {
        return basicMetrological;
    }

    public void setBasicMetrological(String basicMetrological) {
        this.basicMetrological = basicMetrological;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }
}
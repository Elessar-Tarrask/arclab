package com.company.arclab.entity.client;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "JCRM_T_CLIENT_DIGITAL_SIGN")
@Entity(name = "jcrm_TClientDigitalSign")
public class TClientDigitalSign extends StandardEntity {
    private static final long serialVersionUID = -8321214636252269901L;

    @javax.persistence.Column
    private String storagePath;

    @javax.persistence.Column
    private String signatoryType;

    @javax.persistence.Column
    private String signatoryName;

    @javax.persistence.Column
    private String signatoryFirstKey;

    @javax.persistence.Column
    private String signatorySecondKey;

    @javax.persistence.Column
    private String signatoryCN;

    @javax.persistence.Column
    private String signatorySurname;

    @javax.persistence.Column
    private String signatorySerialNumber;

    @javax.persistence.Column
    private String signatoryC;

    @javax.persistence.Column
    private String signatoryG;

    @javax.persistence.Column
    private String signatoryDateFrom;

    @javax.persistence.Column
    private String signatoryDateTill;

    @javax.persistence.Column
    private String signatoryAction;

    public void setSignatoryAction(String signatoryAction) {
        this.signatoryAction = signatoryAction;
    }

    public String getSignatoryAction() {
        return signatoryAction;
    }

    public String getSignatoryDateTill() {
        return signatoryDateTill;
    }

    public void setSignatoryDateTill(String signatoryDateTill) {
        this.signatoryDateTill = signatoryDateTill;
    }

    public String getSignatoryDateFrom() {
        return signatoryDateFrom;
    }

    public void setSignatoryDateFrom(String signatoryDateFrom) {
        this.signatoryDateFrom = signatoryDateFrom;
    }

    public String getSignatoryG() {
        return signatoryG;
    }

    public void setSignatoryG(String signatoryG) {
        this.signatoryG = signatoryG;
    }

    public String getSignatoryC() {
        return signatoryC;
    }

    public void setSignatoryC(String signatoryC) {
        this.signatoryC = signatoryC;
    }

    public String getSignatorySerialNumber() {
        return signatorySerialNumber;
    }

    public void setSignatorySerialNumber(String signatorySerialNumber) {
        this.signatorySerialNumber = signatorySerialNumber;
    }

    public String getSignatorySurname() {
        return signatorySurname;
    }

    public void setSignatorySurname(String signatorySurname) {
        this.signatorySurname = signatorySurname;
    }

    public String getSignatoryCN() {
        return signatoryCN;
    }

    public void setSignatoryCN(String signatoryCN) {
        this.signatoryCN = signatoryCN;
    }

    public String getSignatorySecondKey() {
        return signatorySecondKey;
    }

    public void setSignatorySecondKey(String signatorySecondKey) {
        this.signatorySecondKey = signatorySecondKey;
    }

    public String getSignatoryFirstKey() {
        return signatoryFirstKey;
    }

    public void setSignatoryFirstKey(String signatoryFirstKey) {
        this.signatoryFirstKey = signatoryFirstKey;
    }

    public String getSignatoryName() {
        return signatoryName;
    }

    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }

    public String getSignatoryType() {
        return signatoryType;
    }

    public void setSignatoryType(String signatoryType) {
        this.signatoryType = signatoryType;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
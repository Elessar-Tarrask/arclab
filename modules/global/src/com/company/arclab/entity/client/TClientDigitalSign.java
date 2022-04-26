package com.company.arclab.entity.client;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "JCRM_T_CLIENT_DIGITAL_SIGN")
@Entity(name = "jcrm_TClientDigitalSign")
@NamePattern("%s|signatoryName")
public class TClientDigitalSign extends StandardEntity {
    private static final long serialVersionUID = -8321214636252269901L;

    @Column(name = "STORAGE_PATH")
    private String storagePath;

    @Column(name = "SIGNATORY_TYPE")
    private String signatoryType;

    @Column(name = "SIGNATORY_NAME")
    private String signatoryName;

    @Column(name = "SIGNATORY_FIRST_KEY")
    private String signatoryFirstKey;

    @Column(name = "SIGNATORY_SECOND_KEY")
    private String signatorySecondKey;

    @Column(name = "SIGNATORY_CN")
    private String signatoryCN;

    @Column(name = "SIGNATORY_SURNAME")
    private String signatorySurname;

    @Column(name = "SIGNATORY_SERIAN_NUMBER")
    private String signatorySerialNumber;

    @Column(name = "SIGNATORY_C")
    private String signatoryC;

    @Column(name = "SIGNATORY_G")
    private String signatoryG;

    @Column(name = "SIGNATORY_DATE_FROM")
    private String signatoryDateFrom;

    @Column(name = "SIGNATORY_DATE_TILL")
    private String signatoryDateTill;

    @MetaProperty
    @Transient
    private String signatoryPassword;

    @Column(name = "SIGNATORY_ACTION")
    private String signatoryAction;

    public void setSignatoryAction(String signatoryAction) {
        this.signatoryAction = signatoryAction;
    }

    public String getSignatoryAction() {
        return signatoryAction;
    }

    public void setSignatoryPassword(String signatoryPassword) {
        this.signatoryPassword = signatoryPassword;
    }

    public String getSignatoryPassword() {
        return signatoryPassword;
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
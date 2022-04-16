package com.company.arclab.entity.client;

import com.company.arclab.entity.client.dict.DLicense;
import com.company.arclab.entity.client.dict.DRegDocType;
import com.company.arclab.entity.client.dict.DRegOrg;
import com.company.arclab.entity.client.dict.DTax;
import com.company.arclab.entity.kato.Country;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s %s|docNum,createTs")
@Table(name = "JCRM_T_CLIENT_REGISTER_DOCS")
@Entity(name = "jcrm_TClientRegisterDoc")
public class TClientRegisterDoc extends StandardEntity {
    private static final long serialVersionUID = -3586098697960073375L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_TYPE_ID")
    protected DRegDocType docType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LICENSE_ID")
    private DLicense license;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAX_ID")
    private DTax tax;

    @Column(name = "LONGNAME")
    private String longname;

    @Column(name = "DOC_NUM")
    protected String docNum;

    @Temporal(TemporalType.DATE)
    @Column(name = "FIRST_REG_DATE")
    protected Date firstRegDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DATE")
    protected Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ORF_ID")
    protected DRegOrg regOrf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_COUNTRY_ID")
    protected Country regCountry;

    @Column(name = "LICENSE_NUMBER")
    protected String licenseNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "LICENSE_DATE")
    protected Date licenseDate;

    @Column(name = "LICENSE_VALID_DATE")
    protected String licenseValidDate;

    @Column(name = "LICENSE_ORG")
    protected String licenseOrg;

    @Temporal(TemporalType.DATE)
    @Column(name = "ISSUE_DATE")
    protected Date issueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_DATE")
    protected Date validDate;

    @Column(name = "ISSUE_ORG")
    protected String issueOrg;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NORD")
    private String nord;

    @Column(name = "SER")
    private String ser;

    @Column(name = "NUM")
    private String num;

    @Column(name = "DT_FROM")
    @Temporal(TemporalType.DATE)
    private Date dtFrom;

    @Column(name = "DT_TO")
    @Temporal(TemporalType.DATE)
    private Date dtTo;

    @Column(name = "ARCFL")
    private Boolean arcfl;

    @Column(name = "ORG")
    private String org;

    @Column(name = "REGNUM")
    private String regnum;

    @Column(name = "REGDT")
    @Temporal(TemporalType.DATE)
    private Date regdt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    public void setRegdt(Date regdt) {
        this.regdt = regdt;
    }

    public Date getRegdt() {
        return regdt;
    }

    public void setDtFrom(Date dtFrom) {
        this.dtFrom = dtFrom;
    }

    public Date getDtFrom() {
        return dtFrom;
    }

    public void setDtTo(Date dtTo) {
        this.dtTo = dtTo;
    }

    public Date getDtTo() {
        return dtTo;
    }

    public DTax getTax() {
        return tax;
    }

    public void setTax(DTax tax) {
        this.tax = tax;
    }

    public DLicense getLicense() {
        return license;
    }

    public void setLicense(DLicense license) {
        this.license = license;
    }

    public void setDocType(DRegDocType docType) {
        this.docType = docType;
    }

    public DRegDocType getDocType() {
        return docType;
    }

    public void setArcfl(Boolean arcfl) {
        this.arcfl = arcfl;
    }

    public Boolean getArcfl() {
        return arcfl;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSer() {
        return ser;
    }

    public void setSer(String ser) {
        this.ser = ser;
    }

    public String getNord() {
        return nord;
    }

    public void setNord(String nord) {
        this.nord = nord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocNum() {
        return docNum;
    }



    public String getLicenseOrg() {
        return licenseOrg;
    }

    public void setLicenseOrg(String licenseOrg) {
        this.licenseOrg = licenseOrg;
    }

    public String getLicenseValidDate() {
        return licenseValidDate;
    }

    public void setLicenseValidDate(String licenseValidDate) {
        this.licenseValidDate = licenseValidDate;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public void setRegOrf(DRegOrg regOrf) {
        this.regOrf = regOrf;
    }

    public DRegOrg getRegOrf() {
        return regOrf;
    }

    public String getIssueOrg() {
        return issueOrg;
    }

    public void setIssueOrg(String issueOrg) {
        this.issueOrg = issueOrg;
    }

    public void setFirstRegDate(Date firstRegDate) {
        this.firstRegDate = firstRegDate;
    }

    public Date getFirstRegDate() {
        return firstRegDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Country getRegCountry() {
        return regCountry;
    }

    public void setRegCountry(Country regCountry) {
        this.regCountry = regCountry;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

}
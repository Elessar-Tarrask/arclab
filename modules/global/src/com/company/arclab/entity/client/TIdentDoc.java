package com.company.arclab.entity.client;

import com.company.arclab.entity.client.dict.DIdDoc;
import com.company.arclab.entity.client.dict.DTypeExt;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "JCRM_T_IDENT_DOC")
@Entity(name = "jcrm_TIdentDoc")
@NamePattern("%s %s %s|type,number,createTs")
public class TIdentDoc extends StandardEntity {
    private static final long serialVersionUID = -1039357809442375049L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    private DIdDoc type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_EXT_ID")
    private DTypeExt typeExt;

    @Column(name = "TYPE_EXT_CODE")
    private String typeExtCode;

    @Column(name = "NUMBER_")
    private String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "ISSUE_DATE")
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "ISSUER")
    private String issuer;

    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;

    @Column(name = "IS_ARCHIVAL")
    private Boolean isArchival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getTypeExtCode() {
        return typeExtCode;
    }

    public void setTypeExtCode(String typeExtCode) {
        this.typeExtCode = typeExtCode;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Boolean getIsArchival() {
        return isArchival;
    }

    public void setIsArchival(Boolean isArchival) {
        this.isArchival = isArchival;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public DTypeExt getTypeExt() {
        return typeExt;
    }

    public void setTypeExt(DTypeExt typeExt) {
        this.typeExt = typeExt;
    }

    public DIdDoc getType() {
        return type;
    }

    public void setType(DIdDoc type) {
        this.type = type;
    }
}
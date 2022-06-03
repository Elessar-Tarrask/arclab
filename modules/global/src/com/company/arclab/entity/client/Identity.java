package com.company.arclab.entity.client;

import com.company.arclab.entity.client.acts.TWeatherConditions;
import com.company.arclab.entity.client.dict.EClientStatus;
import com.company.arclab.entity.client.dict.EClientType;
import com.company.arclab.entity.client.dict.ESex;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "ARCLAB_IDENTITY")
@Entity(name = "arclab_Identity")
@NamePattern("%s|name")
public class Identity extends StandardEntity {
    private static final long serialVersionUID = 966016710732002688L;

    @Column(name = "IIN_BIN")
    private String iinBin;

    @OneToMany(mappedBy = "identity")
    private List<TWeatherConditions> tWeatherMeasurements;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DATE")
    private Date regDate;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "SEX")
    private String sex;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "identity")
    @Composition
    protected List<TClientRegisterDoc> registerDocs;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "identity")
    @Composition
    protected List<TClientAddress> contactsAdresses;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "identity")
    @Composition
    private List<TContactPhone> phones;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "identity")
    @Composition
    private List<TClientEmail> emails;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "identity")
    @Composition
    private List<TIdentDoc> identDocs;

    public List<TWeatherConditions> gettWeatherMeasurements() {
        return tWeatherMeasurements;
    }

    public void settWeatherMeasurements(List<TWeatherConditions> tWeatherMeasurements) {
        this.tWeatherMeasurements = tWeatherMeasurements;
    }

    public ESex getSex() {
        return sex == null ? null : ESex.fromId(sex);
    }

    public void setSex(ESex sex) {
        this.sex = sex == null ? null : sex.getId();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public EClientStatus getStatus() {
        return status == null ? null : EClientStatus.fromId(status);
    }

    public void setStatus(EClientStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public EClientType getClientType() {
        return clientType == null ? null : EClientType.fromId(clientType);
    }

    public void setClientType(EClientType clientType) {
        this.clientType = clientType == null ? null : clientType.getId();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIinBin() {
        return iinBin;
    }

    public void setIinBin(String iinBin) {
        this.iinBin = iinBin;
    }

    public List<TClientRegisterDoc> getRegisterDocs() {
        return registerDocs;
    }

    public void setRegisterDocs(List<TClientRegisterDoc> registerDocs) {
        this.registerDocs = registerDocs;
    }

    public List<TClientAddress> getContactsAdresses() {
        return contactsAdresses;
    }

    public void setContactsAdresses(List<TClientAddress> contactsAdresses) {
        this.contactsAdresses = contactsAdresses;
    }

    public List<TContactPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<TContactPhone> phones) {
        this.phones = phones;
    }

    public void setEmails(List<TClientEmail> emails) {
        this.emails = emails;
    }

    public List<TClientEmail> getEmails() {
        return emails;
    }

    public List<TIdentDoc> getIdentDocs() {
        return identDocs;
    }

    public void setIdentDocs(List<TIdentDoc> identDocs) {
        this.identDocs = identDocs;
    }
}
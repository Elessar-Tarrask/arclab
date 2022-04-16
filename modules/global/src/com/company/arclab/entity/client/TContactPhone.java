package com.company.arclab.entity.client;

import com.company.arclab.entity.client.dict.DContactKind;
import com.company.arclab.entity.client.dict.DContactsType;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@DiscriminatorValue("PHONE")
@Table(name = "JCRM_T_CONTACT_PHONE")
@Entity(name = "jcrm_TContactPhone")
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@NamePattern("%s|phone")
public class TContactPhone extends StandardEntity {
    private static final long serialVersionUID = 4231228706656735096L;

    // поле nord в Colvir
    @Column(name = "PHONE_SERIAL_NUMBER")
    private String phoneSerialNumber;

    // необходимо объединить с phoneSerialNumber и назвать nord
//    @Column(name = "CONTACT_DATA_NUMBER")
//    private String contactDataNumber;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    // необходимо удалить и использовать contactKind
//    @Column(name = "KIND_CODE")
//    private String kindCode;

    // необходимо удалить и использовать contactKind
//    @Column(name = "KIND_NAME")
//    private String kindName;

    // необходимо удалить и использовать contactType
//    @Column(name = "TYPE_CODE")
//    private String typeCode;

    // необходимо удалить и использовать contactType
//    @Column(name = "TYPE_NAME")
//    private String typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_TYPE_ID")
    private DContactsType contactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_KIND_ID")
    private DContactKind contactKind;

    // нет аналога в Колвир
    @Column(name = "INTERNAL_PHONE")
    protected String internalPhone;

    @Column(name = "IS_MAIN")
    protected Boolean isMain;

    // зачем эта связь, удалить?
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "T_CLIENT_CONTACT_ID")
//    protected TClientContact tClientContact;

    // зачем эта связь, удалить?
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "T_CLIENT_AUTH_PERSON_ID")
//    private TClientAuthPerson tClientAuthPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    // Примечание/комментарии - загружается из колвир
    @Column(name = "NOTE")
    private String note;

    // Добавлен ли телефон оператором КЦ. Не удаляются при синхронизации с Колвир, не передаются в Колвир
    @Column(name = "IS_ADDED_BY_CONTACT_CENTER")
    private Boolean isAddedByContactCenter;

    public Boolean getIsAddedByContactCenter() {
        return isAddedByContactCenter;
    }

    public void setIsAddedByContactCenter(Boolean isAddedByContactCenter) {
        this.isAddedByContactCenter = isAddedByContactCenter;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

//    public void setTClientContact(TClientContact tClientContact) {
//        this.tClientContact = tClientContact;
//    }

//    public void setTClientAuthPerson(TClientAuthPerson tClientAuthPerson) {
//        this.tClientAuthPerson = tClientAuthPerson;
//    }

    public DContactKind getContactKind() {
        return contactKind;
    }

    public void setContactKind(DContactKind contactKind) {
        this.contactKind = contactKind;
    }

    public DContactsType getContactType() {
        return contactType;
    }

    public void setContactType(DContactsType contactType) {
        this.contactType = contactType;
    }

//    public String getContactDataNumber() {
//        return contactDataNumber;
//    }

//    public void setContactDataNumber(String contactDataNumber) {
//        this.contactDataNumber = contactDataNumber;
//    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

//    public TClientAuthPerson gettClientAuthPerson() {
//        return tClientAuthPerson;
//    }

//    public void settClientAuthPerson(TClientAuthPerson tClientAuthPerson) {
//        this.tClientAuthPerson = tClientAuthPerson;
//    }

    public String getPhoneSerialNumber() {
        return phoneSerialNumber;
    }

    public void setPhoneSerialNumber(String phoneSerialNumber) {
        this.phoneSerialNumber = phoneSerialNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getKindCode() {
//        return kindCode;
//    }

//    public void setKindCode(String kindCode) {
//        this.kindCode = kindCode;
//    }

//    public String getKindName() {
//        return kindName;
//    }

//    public void setKindName(String kindName) {
//        this.kindName = kindName;
//    }

//    public String getTypeCode() {
//        return typeCode;
//    }

//    public void setTypeCode(String typeCode) {
//        this.typeCode = typeCode;
//    }

//    public String getTypeName() {
//        return typeName;
//    }

//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }

    public String getInternalPhone() {
        return internalPhone;
    }

    public void setInternalPhone(String internalPhone) {
        this.internalPhone = internalPhone;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

//    public TClientContact gettClientContact() {
//        return tClientContact;
//    }

//    public void settClientContact(TClientContact tClientContact) {
//        this.tClientContact = tClientContact;
//    }

//    @Override
//    public String toString() {
//        return kindName + ": " + phone;
//    }
}
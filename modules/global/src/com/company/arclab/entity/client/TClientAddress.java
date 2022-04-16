package com.company.arclab.entity.client;

import com.company.arclab.entity.kato.Country;
import com.company.arclab.entity.kato.DAddressType;
import com.company.arclab.entity.kato.DKato;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s / %s|addressType,country,region,city,houseNumber,number")
@Table(name = "JCRM_T_CLIENT_ADRESSES")
@Entity(name = "jcrm_TClientAdress")
public class TClientAddress extends StandardEntity {
    private static final long serialVersionUID = -3075744636750207815L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_TYPE_ID")
    protected DAddressType addressType;

    @Column(name = "ADDR_SERIAL_NUMBER")
    private Integer addrSerialNumber;

//    @Column(name = "ADDRESS_TYPE_CODE")
//    private String addressTypeCode;

    @Column(name = "FULLADDRESS")
    protected String fulladdress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    protected Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    protected DKato region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    protected DKato city;

    @Column(name = "CITY_NAME")
    private String cityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    protected DKato district;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "STREET_ID")
//    protected DStreet street;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCALITY_ID")
    protected DKato locality;

    @Column(name = "STREET_NAME")
    private String streetName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MICRO_DISTRICT_ID")
//    protected DDistrict microDistrict;

    @Column(name = "HOUSE_NUMBER")
    protected String houseNumber;

    @Column(name = "ZIP_CODE")
    protected String zipCode;

    @Column(name = "PRIZN_OSN")
    protected Boolean priznOsn;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CONTACT_TYPE_ID")
//    protected DContactType contactType;

    @Column(name = "NUMBER_")
    protected String number;

//    @Column(name = "DWH_ID")
//    protected Long dwhId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "T_CLIENT_ID")
//    protected TClient tClient;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "T_PERSON_ID")
//    private TPerson tPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLocality(DKato locality) {
        this.locality = locality;
    }

    public DKato getLocality() {
        return locality;
    }

    public void setDistrict(DKato district) {
        this.district = district;
    }

    public DKato getDistrict() {
        return district;
    }

    public void setRegion(DKato region) {
        this.region = region;
    }

    public DKato getRegion() {
        return region;
    }

    public void setCity(DKato city) {
        this.city = city;
    }

    public DKato getCity() {
        return city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

//    public TPerson gettPerson() {
//        return tPerson;
//    }

//    public void settPerson(TPerson tPerson) {
//        this.tPerson = tPerson;
//    }

    public Integer getAddrSerialNumber() {
        return addrSerialNumber;
    }

    public void setAddrSerialNumber(Integer addrSerialNumber) {
        this.addrSerialNumber = addrSerialNumber;
    }

//    public String getAddressTypeCode() {
//        return addressTypeCode;
//    }

//    public void setAddressTypeCode(String addressTypeCode) {
//        this.addressTypeCode = addressTypeCode;
//    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

//    public void setTClient(TClient tClient) {
//        this.tClient = tClient;
//    }

//    public TClient gettClient() {
//        return tClient;
//    }

//    public void settClient(TClient tClient) {
//        this.tClient = tClient;
//    }

    public void setAddressType(DAddressType addressType) {
        this.addressType = addressType;
    }

    public DAddressType getAddressType() {
        return addressType;
    }

//    public void setContactType(DContactType contactType) {
//        this.contactType = contactType;
//    }
//
//    public DContactType getContactType() {
//        return contactType;
//    }

    public void setPriznOsn(Boolean priznOsn) {
        this.priznOsn = priznOsn;
    }

    public Boolean getPriznOsn() {
        return priznOsn;
    }

//    public void setStreet(DStreet street) {
//        this.street = street;
//    }

//    public DStreet getStreet() {
//        return street;
//    }

//    public void setMicroDistrict(DDistrict microDistrict) {
//        this.microDistrict = microDistrict;
//    }

//    public DDistrict getMicroDistrict() {
//        return microDistrict;
//    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

//    public TClient getTClient() {
//        return tClient;
//    }
}
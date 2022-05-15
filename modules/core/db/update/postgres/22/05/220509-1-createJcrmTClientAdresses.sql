create table JCRM_T_CLIENT_ADRESSES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ADDRESS_TYPE_ID uuid,
    ADDR_SERIAL_NUMBER integer,
    FULLADDRESS varchar(255),
    COUNTRY_ID uuid,
    REGION_ID uuid,
    CITY_ID uuid,
    CITY_NAME varchar(255),
    DISTRICT_ID uuid,
    LOCALITY_ID uuid,
    STREET_NAME varchar(255),
    HOUSE_NUMBER varchar(255),
    ZIP_CODE varchar(255),
    PRIZN_OSN boolean,
    NUMBER_ varchar(255),
    IDENTITY_ID uuid,
    --
    primary key (ID)
);
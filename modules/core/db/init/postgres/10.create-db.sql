-- begin JCRM_D_CONTACT_KIND
create table JCRM_D_CONTACT_KIND (
    ID varchar(10),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_CONTACT_KIND
-- begin JCRM_D_TYPE_EXT
create table JCRM_D_TYPE_EXT (
    ID varchar(10),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_TYPE_EXT
-- begin JCRM_D_CONTACTS_TYPE
create table JCRM_D_CONTACTS_TYPE (
    ID varchar(10),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_CONTACTS_TYPE
-- begin JCRM_T_CONTACT_PHONE
create table JCRM_T_CONTACT_PHONE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    PHONE_SERIAL_NUMBER varchar(255),
    PHONE varchar(255),
    CONTACT_NAME varchar(255),
    CONTACT_TYPE_ID varchar(10),
    CONTACT_KIND_ID varchar(10),
    INTERNAL_PHONE varchar(255),
    IS_MAIN boolean,
    IDENTITY_ID uuid,
    NOTE varchar(255),
    IS_ADDED_BY_CONTACT_CENTER boolean,
    --
    primary key (ID)
)^
-- end JCRM_T_CONTACT_PHONE
-- begin ARCLAB_IDENTITY
create table ARCLAB_IDENTITY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IIN_BIN varchar(255),
    FULL_NAME varchar(255),
    CLIENT_TYPE varchar(50),
    STATUS varchar(50),
    REG_DATE date,
    NAME varchar(255),
    MIDDLE_NAME varchar(255),
    LAST_NAME varchar(255),
    BIRTH_DATE date,
    SEX varchar(50),
    --
    primary key (ID)
)^
-- end ARCLAB_IDENTITY
-- begin ARCLAB_MEASURING_INSTRUMENTS
create table ARCLAB_MEASURING_INSTRUMENTS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CHARACTERISTIC_NAME varchar(255),
    SERIAL_NUMBER varchar(255),
    BASIC_METROLOGICAL varchar(255),
    MANUFACTURE_YEAR date,
    CERTIFICATE_NUMBER varchar(255),
    CERTIFICATE_DATE date,
    STATE_SYSTEM_NUMBER varchar(255),
    --
    primary key (ID)
)^
-- end ARCLAB_MEASURING_INSTRUMENTS
-- begin JCRM_D_LICENSE
create table JCRM_D_LICENSE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_LICENSE
-- begin JCRM_D_ID_DOC
create table JCRM_D_ID_DOC (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_ID_DOC
-- begin JCRM_T_CLIENT_ADRESSES
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
)^
-- end JCRM_T_CLIENT_ADRESSES
-- begin JCRM_KATO_COUNTRY
create table JCRM_KATO_COUNTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    CODE_WAY4 varchar(255),
    COUNTRY_NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_KATO_COUNTRY
-- begin JCRM_D_TAX
create table JCRM_D_TAX (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    COLVIR_ID varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_TAX
-- begin JCRM_T_CLIENT_REGISTER_DOCS
create table JCRM_T_CLIENT_REGISTER_DOCS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOC_TYPE_ID uuid,
    LICENSE_ID uuid,
    TAX_ID uuid,
    LONGNAME varchar(255),
    DOC_NUM varchar(255),
    FIRST_REG_DATE date,
    REG_DATE date,
    REG_ORF_ID uuid,
    REG_COUNTRY_ID uuid,
    LICENSE_NUMBER varchar(255),
    LICENSE_DATE date,
    LICENSE_VALID_DATE varchar(255),
    LICENSE_ORG varchar(255),
    ISSUE_DATE date,
    VALID_DATE date,
    ISSUE_ORG varchar(255),
    CODE varchar(255),
    NORD varchar(255),
    SER varchar(255),
    NUM varchar(255),
    DT_FROM date,
    DT_TO date,
    ARCFL boolean,
    ORG varchar(255),
    REGNUM varchar(255),
    REGDT date,
    IDENTITY_ID uuid,
    --
    primary key (ID)
)^
-- end JCRM_T_CLIENT_REGISTER_DOCS
-- begin JCRM_D_KATO
create table JCRM_D_KATO (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PARENT_ID uuid,
    KATO_CODE bigint not null,
    KATO_NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_KATO
-- begin JCRM_T_IDENT_DOC
create table JCRM_T_IDENT_DOC (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ID uuid,
    TYPE_EXT_ID varchar(10),
    TYPE_EXT_CODE varchar(255),
    NUMBER_ varchar(255),
    ISSUE_DATE date,
    EXPIRATION_DATE date,
    ISSUER varchar(255),
    IS_DEFAULT boolean,
    IS_ARCHIVAL boolean,
    IDENTITY_ID uuid,
    --
    primary key (ID)
)^
-- end JCRM_T_IDENT_DOC
-- begin JCRM_D_ADDRESS_TYPE
create table JCRM_D_ADDRESS_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_ADDRESS_TYPE
-- begin JCRM_D_REG_DOC_TYPE
create table JCRM_D_REG_DOC_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_REG_DOC_TYPE
-- begin JCRM_D_REG_ORG
create table JCRM_D_REG_ORG (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_D_REG_ORG
-- begin JCRM_KATO_CITY
create table JCRM_KATO_CITY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CITY_NAME varchar(255),
    CODE varchar(255),
    VALUE_ varchar(255),
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_KATO_CITY
-- begin JCRM_T_CLIENT_EMAIL
create table JCRM_T_CLIENT_EMAIL (
    ID uuid,
    --
    EMAIL varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_T_CLIENT_EMAIL
-- begin JCRM_DOC_FORMED
create table JCRM_DOC_FORMED (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uuid,
    DOC_TYPE varchar(255),
    DOC_FILE_ID uuid,
    DOC_STATUS varchar(50),
    --
    primary key (ID)
)^
-- end JCRM_DOC_FORMED
-- begin JCRM_APPLICATION
create table JCRM_APPLICATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    NAME varchar(255),
    IS_JB boolean not null,
    MANAGER_ID uuid,
    REQ_ID bigint,
    PROC_ID varchar(255),
    INFO text,
    APPLICATION_STATUS varchar(255),
    IIN_BIN varchar(255),
    FULL_NAME varchar(255),
    DATE_OF_COMPLETION date,
    --
    primary key (ID)
)^
-- end JCRM_APPLICATION
-- begin JCRM_T_CLIENT_DIGITAL_SIGN
create table JCRM_T_CLIENT_DIGITAL_SIGN (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STORAGEPATH varchar(255),
    SIGNATORYTYPE varchar(255),
    SIGNATORYNAME varchar(255),
    SIGNATORYFIRSTKEY varchar(255),
    SIGNATORYSECONDKEY varchar(255),
    SIGNATORYCN varchar(255),
    SIGNATORYSURNAME varchar(255),
    SIGNATORYSERIALNUMBER varchar(255),
    SIGNATORYC varchar(255),
    SIGNATORYG varchar(255),
    SIGNATORYDATEFROM varchar(255),
    SIGNATORYDATETILL varchar(255),
    SIGNATORYACTION varchar(255),
    --
    primary key (ID)
)^
-- end JCRM_T_CLIENT_DIGITAL_SIGN
-- begin SEC_USER
alter table SEC_USER add column MANAGER_NETWORK_NAME varchar(255) ^
alter table SEC_USER add column EMPLOYEE_ID varchar(255) ^
alter table SEC_USER add column PHONE varchar(255) ^
alter table SEC_USER add column DIGITAL_SIGN varchar(255) ^
alter table SEC_USER add column DTYPE varchar(31) ^
update SEC_USER set DTYPE = 'TMANAGER' where DTYPE is null ^
-- end SEC_USER

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
    STORAGE_PATH varchar(255),
    SIGNATORY_TYPE varchar(255),
    SIGNATORY_NAME varchar(255),
    SIGNATORY_FIRST_KEY varchar(255),
    SIGNATORY_SECOND_KEY varchar(255),
    SIGNATORY_CN varchar(255),
    SIGNATORY_SURNAME varchar(255),
    SIGNATORY_SERIAN_NUMBER varchar(255),
    SIGNATORY_C varchar(255),
    SIGNATORY_G varchar(255),
    SIGNATORY_DATE_FROM varchar(255),
    SIGNATORY_DATE_TILL varchar(255),
    SIGNATORY_ACTION varchar(255),
    --
    primary key (ID)
);
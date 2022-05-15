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
);
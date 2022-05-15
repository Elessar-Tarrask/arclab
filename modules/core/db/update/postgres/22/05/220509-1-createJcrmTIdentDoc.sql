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
);
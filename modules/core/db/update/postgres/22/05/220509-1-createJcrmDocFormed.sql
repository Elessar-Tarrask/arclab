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
);
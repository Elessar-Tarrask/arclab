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
);
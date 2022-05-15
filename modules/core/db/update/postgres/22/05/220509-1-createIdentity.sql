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
);
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
);
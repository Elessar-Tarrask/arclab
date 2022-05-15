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
);
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
);
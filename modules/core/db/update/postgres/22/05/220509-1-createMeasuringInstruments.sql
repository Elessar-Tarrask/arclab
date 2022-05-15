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
);
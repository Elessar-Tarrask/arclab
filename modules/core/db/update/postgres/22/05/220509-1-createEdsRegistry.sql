create table ARCLAB_EDS_REGISTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FILE_TO_SIGN_ID uuid,
    SIGN_ACTION varchar(255),
    SIGN_COMMENT varchar(255),
    CURRENT_USER_ID uuid,
    SIGNED_XML text,
    DATE_TIME timestamp,
    SIGNER_FIO varchar(255),
    SIGNER_IIN_BIN varchar(255),
    --
    primary key (ID)
);
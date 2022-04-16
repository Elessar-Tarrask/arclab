create table JCRM_T_CLIENT_DIGITAL_SIGN (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STORAGEPATH varchar(255),
    SIGNATORYTYPE varchar(255),
    SIGNATORYNAME varchar(255),
    SIGNATORYFIRSTKEY varchar(255),
    SIGNATORYSECONDKEY varchar(255),
    SIGNATORYCN varchar(255),
    SIGNATORYSURNAME varchar(255),
    SIGNATORYSERIALNUMBER varchar(255),
    SIGNATORYC varchar(255),
    SIGNATORYG varchar(255),
    SIGNATORYDATEFROM varchar(255),
    SIGNATORYDATETILL varchar(255),
    SIGNATORYACTION varchar(255),
    --
    primary key (ID)
);
alter table JCRM_APPLICATION add constraint FK_JCRM_APPLICATION_ON_MANAGER foreign key (MANAGER_ID) references SEC_USER(ID);
create unique index IDX_JCRM_APPLICATION_ID_UNQ on JCRM_APPLICATION (ID) where DELETE_TS is null ;
create index IDX_JCRM_APPLICATION_ON_MANAGER on JCRM_APPLICATION (MANAGER_ID);

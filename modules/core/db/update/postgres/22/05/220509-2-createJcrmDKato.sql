alter table JCRM_D_KATO add constraint FK_JCRM_D_KATO_ON_PARENT foreign key (PARENT_ID) references JCRM_D_KATO(ID);
create unique index IDX_JCRM_D_KATO_UK_KATO_CODE on JCRM_D_KATO (KATO_CODE) where DELETE_TS is null ;
create index IDX_JCRM_D_KATO_ON_PARENT on JCRM_D_KATO (PARENT_ID);

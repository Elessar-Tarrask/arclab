-- begin JCRM_T_CONTACT_PHONE
alter table JCRM_T_CONTACT_PHONE add constraint FK_JCRM_T_CONTACT_PHONE_ON_CONTACT_TYPE foreign key (CONTACT_TYPE_ID) references JCRM_D_CONTACTS_TYPE(ID)^
alter table JCRM_T_CONTACT_PHONE add constraint FK_JCRM_T_CONTACT_PHONE_ON_CONTACT_KIND foreign key (CONTACT_KIND_ID) references JCRM_D_CONTACT_KIND(ID)^
alter table JCRM_T_CONTACT_PHONE add constraint FK_JCRM_T_CONTACT_PHONE_ON_IDENTITY foreign key (IDENTITY_ID) references ARCLAB_IDENTITY(ID)^
create index IDX_JCRM_T_CONTACT_PHONE_ON_CONTACT_TYPE on JCRM_T_CONTACT_PHONE (CONTACT_TYPE_ID)^
create index IDX_JCRM_T_CONTACT_PHONE_ON_CONTACT_KIND on JCRM_T_CONTACT_PHONE (CONTACT_KIND_ID)^
create index IDX_JCRM_T_CONTACT_PHONE_ON_IDENTITY on JCRM_T_CONTACT_PHONE (IDENTITY_ID)^
-- end JCRM_T_CONTACT_PHONE
-- begin JCRM_D_LICENSE
create unique index IDX_JCRM_D_LICENSE_UK_CODE on JCRM_D_LICENSE (CODE) where DELETE_TS is null ^
-- end JCRM_D_LICENSE
-- begin JCRM_D_ID_DOC
create unique index IDX_JCRM_D_ID_DOC_UK_CODE on JCRM_D_ID_DOC (CODE) where DELETE_TS is null ^
-- end JCRM_D_ID_DOC
-- begin JCRM_T_CLIENT_ADRESSES
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_ADDRESS_TYPE foreign key (ADDRESS_TYPE_ID) references JCRM_D_ADDRESS_TYPE(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_COUNTRY foreign key (COUNTRY_ID) references JCRM_KATO_COUNTRY(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_REGION foreign key (REGION_ID) references JCRM_D_KATO(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_CITY foreign key (CITY_ID) references JCRM_D_KATO(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_DISTRICT foreign key (DISTRICT_ID) references JCRM_D_KATO(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_LOCALITY foreign key (LOCALITY_ID) references JCRM_D_KATO(ID)^
alter table JCRM_T_CLIENT_ADRESSES add constraint FK_JCRM_T_CLIENT_ADRESSES_ON_IDENTITY foreign key (IDENTITY_ID) references ARCLAB_IDENTITY(ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_ADDRESS_TYPE on JCRM_T_CLIENT_ADRESSES (ADDRESS_TYPE_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_COUNTRY on JCRM_T_CLIENT_ADRESSES (COUNTRY_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_REGION on JCRM_T_CLIENT_ADRESSES (REGION_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_CITY on JCRM_T_CLIENT_ADRESSES (CITY_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_DISTRICT on JCRM_T_CLIENT_ADRESSES (DISTRICT_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_LOCALITY on JCRM_T_CLIENT_ADRESSES (LOCALITY_ID)^
create index IDX_JCRM_T_CLIENT_ADRESSES_ON_IDENTITY on JCRM_T_CLIENT_ADRESSES (IDENTITY_ID)^
-- end JCRM_T_CLIENT_ADRESSES
-- begin JCRM_KATO_COUNTRY
create unique index IDX_JCRM_KATO_COUNTRY_UK_CODE on JCRM_KATO_COUNTRY (CODE) where DELETE_TS is null ^
create unique index IDX_JCRM_KATO_COUNTRY_UK_CODE_WAY4 on JCRM_KATO_COUNTRY (CODE_WAY4) where DELETE_TS is null ^
-- end JCRM_KATO_COUNTRY
-- begin JCRM_D_TAX
create unique index IDX_JCRM_D_TAX_UK_CODE on JCRM_D_TAX (CODE) where DELETE_TS is null ^
-- end JCRM_D_TAX
-- begin JCRM_T_CLIENT_REGISTER_DOCS
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_DOC_TYPE foreign key (DOC_TYPE_ID) references JCRM_D_REG_DOC_TYPE(ID)^
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_LICENSE foreign key (LICENSE_ID) references JCRM_D_LICENSE(ID)^
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_TAX foreign key (TAX_ID) references JCRM_D_TAX(ID)^
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_REG_ORF foreign key (REG_ORF_ID) references JCRM_D_REG_ORG(ID)^
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_REG_COUNTRY foreign key (REG_COUNTRY_ID) references JCRM_KATO_COUNTRY(ID)^
alter table JCRM_T_CLIENT_REGISTER_DOCS add constraint FK_JCRM_T_CLIENT_REGISTER_DOCS_ON_IDENTITY foreign key (IDENTITY_ID) references ARCLAB_IDENTITY(ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_DOC_TYPE on JCRM_T_CLIENT_REGISTER_DOCS (DOC_TYPE_ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_LICENSE on JCRM_T_CLIENT_REGISTER_DOCS (LICENSE_ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_TAX on JCRM_T_CLIENT_REGISTER_DOCS (TAX_ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_REG_ORF on JCRM_T_CLIENT_REGISTER_DOCS (REG_ORF_ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_REG_COUNTRY on JCRM_T_CLIENT_REGISTER_DOCS (REG_COUNTRY_ID)^
create index IDX_JCRM_T_CLIENT_REGISTER_DOCS_ON_IDENTITY on JCRM_T_CLIENT_REGISTER_DOCS (IDENTITY_ID)^
-- end JCRM_T_CLIENT_REGISTER_DOCS
-- begin JCRM_D_KATO
alter table JCRM_D_KATO add constraint FK_JCRM_D_KATO_ON_PARENT foreign key (PARENT_ID) references JCRM_D_KATO(ID)^
create unique index IDX_JCRM_D_KATO_UK_KATO_CODE on JCRM_D_KATO (KATO_CODE) where DELETE_TS is null ^
create index IDX_JCRM_D_KATO_ON_PARENT on JCRM_D_KATO (PARENT_ID)^
-- end JCRM_D_KATO
-- begin JCRM_T_IDENT_DOC
alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_TYPE foreign key (TYPE_ID) references JCRM_D_ID_DOC(ID)^
alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_TYPE_EXT foreign key (TYPE_EXT_ID) references JCRM_D_TYPE_EXT(ID)^
alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_IDENTITY foreign key (IDENTITY_ID) references ARCLAB_IDENTITY(ID)^
create index IDX_JCRM_T_IDENT_DOC_ON_TYPE on JCRM_T_IDENT_DOC (TYPE_ID)^
create index IDX_JCRM_T_IDENT_DOC_ON_TYPE_EXT on JCRM_T_IDENT_DOC (TYPE_EXT_ID)^
create index IDX_JCRM_T_IDENT_DOC_ON_IDENTITY on JCRM_T_IDENT_DOC (IDENTITY_ID)^
-- end JCRM_T_IDENT_DOC
-- begin JCRM_D_ADDRESS_TYPE
create unique index IDX_JCRM_D_ADDRESS_TYPE_UK_CODE on JCRM_D_ADDRESS_TYPE (CODE) where DELETE_TS is null ^
-- end JCRM_D_ADDRESS_TYPE
-- begin JCRM_D_REG_DOC_TYPE
create unique index IDX_JCRM_D_REG_DOC_TYPE_UK_CODE on JCRM_D_REG_DOC_TYPE (CODE) where DELETE_TS is null ^
-- end JCRM_D_REG_DOC_TYPE
-- begin JCRM_T_CLIENT_EMAIL
alter table JCRM_T_CLIENT_EMAIL add constraint FK_JCRM_T_CLIENT_EMAIL_ON_ID foreign key (ID) references JCRM_T_CONTACT_PHONE(ID) on delete CASCADE^
-- end JCRM_T_CLIENT_EMAIL
-- begin JCRM_DOC_FORMED
alter table JCRM_DOC_FORMED add constraint FK_JCRM_DOC_FORMED_ON_APPLICATION foreign key (APPLICATION_ID) references JCRM_APPLICATION(ID)^
alter table JCRM_DOC_FORMED add constraint FK_JCRM_DOC_FORMED_ON_DOC_FILE foreign key (DOC_FILE_ID) references SYS_FILE(ID)^
create unique index IDX_JCRM_DOC_FORMED_UK_DOC_FILE_ID on JCRM_DOC_FORMED (DOC_FILE_ID) where DELETE_TS is null ^
create index IDX_JCRM_DOC_FORMED_ON_APPLICATION on JCRM_DOC_FORMED (APPLICATION_ID)^
create index IDX_JCRM_DOC_FORMED_ON_DOC_FILE on JCRM_DOC_FORMED (DOC_FILE_ID)^
-- end JCRM_DOC_FORMED
-- begin JCRM_APPLICATION
alter table JCRM_APPLICATION add constraint FK_JCRM_APPLICATION_ON_MANAGER foreign key (MANAGER_ID) references SEC_USER(ID)^
create unique index IDX_JCRM_APPLICATION_ID_UNQ on JCRM_APPLICATION (ID) where DELETE_TS is null ^
create index IDX_JCRM_APPLICATION_ON_MANAGER on JCRM_APPLICATION (MANAGER_ID)^
-- end JCRM_APPLICATION
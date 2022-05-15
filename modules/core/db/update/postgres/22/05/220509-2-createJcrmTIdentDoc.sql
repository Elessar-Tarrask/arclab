alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_TYPE foreign key (TYPE_ID) references JCRM_D_ID_DOC(ID);
alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_TYPE_EXT foreign key (TYPE_EXT_ID) references JCRM_D_TYPE_EXT(ID);
alter table JCRM_T_IDENT_DOC add constraint FK_JCRM_T_IDENT_DOC_ON_IDENTITY foreign key (IDENTITY_ID) references ARCLAB_IDENTITY(ID);
create index IDX_JCRM_T_IDENT_DOC_ON_TYPE on JCRM_T_IDENT_DOC (TYPE_ID);
create index IDX_JCRM_T_IDENT_DOC_ON_TYPE_EXT on JCRM_T_IDENT_DOC (TYPE_EXT_ID);
create index IDX_JCRM_T_IDENT_DOC_ON_IDENTITY on JCRM_T_IDENT_DOC (IDENTITY_ID);

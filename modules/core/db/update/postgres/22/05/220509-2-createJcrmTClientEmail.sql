alter table JCRM_T_CLIENT_EMAIL add constraint FK_JCRM_T_CLIENT_EMAIL_ON_ID foreign key (ID) references JCRM_T_CONTACT_PHONE(ID) on delete CASCADE;

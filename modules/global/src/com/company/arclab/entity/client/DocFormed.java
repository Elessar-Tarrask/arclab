package com.company.arclab.entity.client;

import com.company.arclab.entity.application.Application;
import com.company.arclab.entity.application.EDocStatus;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "JCRM_DOC_FORMED")
@Entity(name = "jcrm_DocFormed")
public class DocFormed extends StandardEntity {
    private static final long serialVersionUID = 65666032342891991L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID")
    protected Application application;

    @Column(name = "DOC_TYPE")
    protected String docType;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_FILE_ID", unique = true)
    protected FileDescriptor docFile;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CLIENT_APPLICATION_ID")
//    private ClientApplication clientApplication;

    @Column(name = "DOC_STATUS")
    protected String docStatus = EDocStatus.ACTIVE.name();

    public void setDocStatus(EDocStatus docStatus) {
        this.docStatus = docStatus == null ? null : docStatus.getId();
    }

    public EDocStatus getDocStatus() {
        return docStatus == null ? null : EDocStatus.fromId(docStatus);
    }

//    public ClientApplication getClientApplication() {
//        return clientApplication;
//    }
//
//    public void setClientApplication(ClientApplication clientApplication) {
//        this.clientApplication = clientApplication;
//    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public FileDescriptor getDocFile() {
        return docFile;
    }

    public void setDocFile(FileDescriptor docFile) {
        this.docFile = docFile;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

}
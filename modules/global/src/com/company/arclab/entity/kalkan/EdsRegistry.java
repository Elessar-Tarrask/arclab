package com.company.arclab.entity.kalkan;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "ARCLAB_EDS_REGISTRY")
@Entity(name = "arclab_EdsRegistry")
@NamePattern("%s|signerFio")
public class EdsRegistry extends StandardEntity {
    private static final long serialVersionUID = -4471048969668301157L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_TO_SIGN_ID")
    private FileDescriptor fileToSign;

    @Column(name = "SIGN_ACTION")
    private String signAction;

    @Column(name = "SIGN_COMMENT")
    private String signComment;

    @Transient
    @MetaProperty
    private String fileCheckSum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_USER_ID")
    private User currentUser;

    @Lob
    @Column(name = "SIGNED_XML")
    private String signedCmsWithFileInfo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME")
    private Date signDateTime;

    @Column(name = "SIGNER_FIO")
    private String signerFio;

    @Column(name = "SIGNER_IIN_BIN")
    private String signerIinBin;

    @Transient
    @MetaProperty
    private Boolean signResult;

    @MetaProperty
    @Transient
    private String signErrorMessage;

    public String getSignAction() {
        return signAction;
    }

    public void setSignAction(String signAction) {
        this.signAction = signAction;
    }

    public void setSignResult(Boolean signResult) {
        this.signResult = signResult;
    }

    public Boolean getSignResult() {
        return signResult;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getSignErrorMessage() {
        return signErrorMessage;
    }

    public void setSignErrorMessage(String signErrorMessage) {
        this.signErrorMessage = signErrorMessage;
    }

    public String getFileCheckSum() {
        return fileCheckSum;
    }

    public void setFileCheckSum(String fileCheckSum) {
        this.fileCheckSum = fileCheckSum;
    }

    public String getSignerIinBin() {
        return signerIinBin;
    }

    public void setSignerIinBin(String signerIinBin) {
        this.signerIinBin = signerIinBin;
    }

    public String getSignerFio() {
        return signerFio;
    }

    public void setSignerFio(String signerFio) {
        this.signerFio = signerFio;
    }

    public String getSignComment() {
        return signComment;
    }

    public void setSignComment(String signComment) {
        this.signComment = signComment;
    }

    public FileDescriptor getFileToSign() {
        return fileToSign;
    }

    public void setFileToSign(FileDescriptor fileToSign) {
        this.fileToSign = fileToSign;
    }

    public String getSignedCmsWithFileInfo() {
        return signedCmsWithFileInfo;
    }

    public void setSignedCmsWithFileInfo(String signedCmsWithFileInfo) {
        this.signedCmsWithFileInfo = signedCmsWithFileInfo;
    }

    public Date getSignDateTime() {
        return signDateTime;
    }

    public void setSignDateTime(Date signDateTime) {
        this.signDateTime = signDateTime;
    }
}
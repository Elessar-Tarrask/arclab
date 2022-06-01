package com.company.arclab.entity.application;

import com.company.arclab.entity.client.Identity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "ARCLAB_IDENTITY_APPLICATION")
@Entity(name = "arclab_IdentityApplication")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@NamePattern("%s / %s|reqId,iinBin")
public class IdentityApplication extends Application {
    private static final long serialVersionUID = -6091953125488063453L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_ID")
    private Identity identity;

    @NotNull
    @Column(name = "APPLICATION_TYPE", nullable = false)
    private String applicationType;

    public EClientApplicationType getApplicationType() {
        return applicationType == null ? null : EClientApplicationType.fromId(applicationType);
    }

    public void setApplicationType(EClientApplicationType applicationType) {
        this.applicationType = applicationType == null ? null : applicationType.getId();
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
}
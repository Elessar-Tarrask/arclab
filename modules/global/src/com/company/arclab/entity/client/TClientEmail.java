package com.company.arclab.entity.client;

import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;
import javax.validation.constraints.Email;

@DiscriminatorValue("EMAIL")
@Table(name = "JCRM_T_CLIENT_EMAIL")
@Entity(name = "jcrm_TClientEmail")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@NamePattern("%s %s|phone,email")
public class TClientEmail extends TContactPhone {
    private static final long serialVersionUID = 8550039456372174071L;

    @Column(name = "EMAIL")
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
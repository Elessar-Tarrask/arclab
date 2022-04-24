package com.company.arclab.entity.application;

import com.company.arclab.entity.client.DocFormed;
import com.company.arclab.entity.client.TManager;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;

@PublishEntityChangedEvents
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "JCRM_APPLICATION", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_JCRM_APPLICATION_ID_UNQ", columnNames = {"ID"})
})
@Entity(name = "jcrm_Application")
@NamePattern("%s  / %s|reqId")
@Listeners("jcrm_ApplicationEntityListener")
public class Application extends StandardEntity {
    private static final long serialVersionUID = -991923662665880784L;

    public static final String VIEW_FOR_DECLINING_APPLICATION_AND_SENDING_EMAIL = "view-for-declining-application-and-sending-email";

    public static final String SEQUENCE = "applicationSequence";

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    protected TManager manager;

    @Column(name = "REQ_ID")
    protected Long reqId;

    // Ссылка на связанный процесс - для отчётности и устранения поиска экземпляра процесса
    @Column(name = "PROC_ID")
    protected String procId;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    protected List<DocFormed> formedDocs; //Сформированные документы (т.е. автоматически сгенерированные документы на основе шаблонов)

    //вынесены условия финансирования в CreditApplication, т.к. относятся к заявкам по Кредитам / Гарантиям

    @Column(name = "INFO")
    @Lob
    private String info;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "IIN_BIN")
    protected String iinBin;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_COMPLETION")
    private Date dateOfCompletion;

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(Date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public TManager getManager() {
        return manager;
    }

    public void setManager(TManager manager) {
        this.manager = manager;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

//    public Set<TClient> getAffilateRegistry() {
//        return affilateRegistry;
//    }

//    public void setAffilateRegistry(Set<TClient> affilateRegistry) {
//        this.affilateRegistry = affilateRegistry;
//    }

    public List<DocFormed> getFormedDocs() {
        return formedDocs;
    }

    public void setFormedDocs(List<DocFormed> formedDocs) {
        this.formedDocs = formedDocs;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIinBin() {
        return iinBin;
    }

    public void setIinBin(String iinBin) {
        this.iinBin = iinBin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
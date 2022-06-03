package com.company.arclab.event;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;

public class UpdateEcpListEvent extends GlobalApplicationEvent implements GlobalUiEvent {

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    private String checkSum;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    private String outcome;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UpdateEcpListEvent(Object source, User currentUser, String outcome, String checkSum) {
        super(source);
        this.currentUser = currentUser;
        this.outcome = outcome;
        this.checkSum = checkSum;
    }
}

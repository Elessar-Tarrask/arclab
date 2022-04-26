package com.company.arclab.event;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;

public class UpdateEcpListEvent extends GlobalApplicationEvent implements GlobalUiEvent {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    private Entity entity;

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
    public UpdateEcpListEvent(Object source, User currentUser, Entity entity, String outcome) {
        super(source);
        this.currentUser = currentUser;
        this.entity = entity;
        this.outcome = outcome;
    }
}
package com.company.arclab.event;

import com.company.arclab.entity.application.IdentityApplication;
import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;


public class ServiceStateEvent extends GlobalApplicationEvent implements GlobalUiEvent {


    private IdentityApplication clientApplication;
    private String message;

    public ServiceStateEvent(Object source, IdentityApplication clientApplication, String message) {
        super(source);
        this.clientApplication = clientApplication;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public IdentityApplication getClientApplication() {
        return clientApplication;
    }
}

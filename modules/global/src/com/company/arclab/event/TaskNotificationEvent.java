package com.company.arclab.event;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;
import com.haulmont.cuba.security.entity.User;

public class TaskNotificationEvent extends GlobalApplicationEvent implements GlobalUiEvent {
    private Long generalTaskCount;
    private Long creditCommitteeCount;
    private User user;

    public TaskNotificationEvent(Object source, Long generalTaskCount, Long creditCommitteeCount, User user) {
        super(source);
        this.generalTaskCount = generalTaskCount;
        this.creditCommitteeCount = creditCommitteeCount;
        this.user = user;
    }

    public Long getGeneralTaskCount() {
        return generalTaskCount;
    }

    public Long getCreditCommitteeCount() {
        return creditCommitteeCount;
    }

    public User getUser() {
        return user;
    }
}

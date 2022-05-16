package com.company.arclab.event;

import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;

public class GlobalUserAssignedEvent extends GlobalApplicationEvent implements GlobalUiEvent {
    protected TaskData data;

    public GlobalUserAssignedEvent(Object source, TaskData data) {
        super(source);
        this.data = data;
    }

    public TaskData getData() {
        return data;
    }
}

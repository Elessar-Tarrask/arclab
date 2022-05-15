package com.company.arclab.service;

import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.TaskData;

public interface ApplicationService {
    String NAME = "arclab_ApplicationService";

    boolean isActiveApp(Application application);

    public TaskData getActiveTaskAnyUser(String processId);

    TaskData getCurrentTask(String processId);
}
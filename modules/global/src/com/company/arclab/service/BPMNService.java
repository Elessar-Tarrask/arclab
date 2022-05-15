package com.company.arclab.service;

import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.entity.TaskData;

public interface BPMNService {
    String NAME = "arclab_BPMNService";

    ProcessInstanceData getProcessInstanceByApp(Application application, boolean findSubprocess);

    String getTaskDescription(TaskData task);

}
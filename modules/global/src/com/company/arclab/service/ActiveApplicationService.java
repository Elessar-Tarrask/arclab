package com.company.arclab.service;

import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.cuba.security.entity.User;

import java.util.List;

public interface ActiveApplicationService {
    String NAME = "arclab_ActiveApplicationService";

    List<TaskData> getActiveTasks(User user);

    void publishTasksCount(User user);
}
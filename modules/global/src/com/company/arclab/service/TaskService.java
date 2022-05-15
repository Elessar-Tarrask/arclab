package com.company.arclab.service;

import com.haulmont.addon.bproc.entity.TaskData;

import java.util.List;

public interface TaskService {
    String NAME = "arclab_TaskService";

    List<TaskData> getActiveTasksByProcess(String processId);

    List<TaskData> getActiveTasksByProcessAnyUser(String processId);
}
package com.company.arclab.service;

import com.company.arclab.MyApplicationTaskDto;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.cuba.security.entity.User;

import java.util.List;
import java.util.UUID;

public interface ActiveApplicationService {
    String NAME = "arclab_ActiveApplicationService";

    List<TaskData> getActiveTasks(User user);

    List<String> getUserGroupCodes(User user);

    void publishTasksCount(User user);

    List<String> getTaskNameOptionsList(UUID userID);

    List<String> getApplicationTypeOptionsList(UUID userID);

    List<MyApplicationTaskDto> getUserApplicationTasks(UUID userID, String selectedTask, String selectedType, String selectedIinBin, Integer offset, Integer limit);

    List<MyApplicationTaskDto> getUserApplicationTasks(UUID userID, String selectedIinBin, String selectedType, String selectedTask, Long reqId, Integer offset, Integer limit);
}
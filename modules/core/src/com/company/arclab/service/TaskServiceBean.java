package com.company.arclab.service;

import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.service.BprocTaskService;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(TaskService.NAME)
public class TaskServiceBean implements TaskService {

    @Inject
    private BprocTaskService bprocTaskService;
    @Inject
    private UserSessionSource userSessionSource;

    @Override
    public List<TaskData> getActiveTasksByProcess(String processId) {
        if (processId == null)
            return null;
        return bprocTaskService.createTaskDataQuery()
                .taskAssignee(userSessionSource.getUserSession().getCurrentOrSubstitutedUser())
                .processInstanceId(processId)
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    @Override
    public List<TaskData> getActiveTasksByProcessAnyUser(String processId) {
        if (processId == null)
            return null;
        return bprocTaskService.createTaskDataQuery()
                .processInstanceId(processId)
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
    }
}
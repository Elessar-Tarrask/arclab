package com.company.arclab.service;

import com.company.arclab.entity.application.Application;
import com.company.arclab.entity.application.EApplicationStatus;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(ApplicationService.NAME)
public class ApplicationServiceBean implements ApplicationService {

    @Inject
    private TaskService taskService;
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private Logger log;

    @Override
    public boolean isActiveApp(Application application) {
        return application != null && !EApplicationStatus.CANCELED.equals(application.getApplicationStatus())
                && !EApplicationStatus.REJECTED.equals(application.getApplicationStatus())
                && !EApplicationStatus.FINISHED.equals(application.getApplicationStatus());
    }

    @Override
    public TaskData getActiveTaskAnyUser(String processId) {
        if (processId == null)
            return null;
        TaskData task = null;
        try {
            List<TaskData> tasks = taskService.getActiveTasksByProcessAnyUser(processId);
            ProcessInstanceData subProcessInstance = getSubProcessInstanceData(processId);
            if (subProcessInstance != null)
                tasks.addAll(taskService.getActiveTasksByProcessAnyUser(subProcessInstance.getId()));
            if (!tasks.isEmpty()) {
                task = tasks.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public TaskData getCurrentTask(String processId) {
        if (processId == null)
            return null;
        TaskData task = null;
        try {
            List<TaskData> tasks = taskService.getActiveTasksByProcess(processId);
            ProcessInstanceData subProcessInstance = getSubProcessInstanceData(processId);
            if (subProcessInstance != null)
                tasks.addAll(taskService.getActiveTasksByProcess(subProcessInstance.getId()));
            // выполнять будем первую активную задачу
            if (!tasks.isEmpty()) {
                task = tasks.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return task;
    }

    private ProcessInstanceData getSubProcessInstanceData(String processId) {
        return bprocRuntimeService.createProcessInstanceDataQuery()
                .superProcessInstanceId(processId)
                .active().singleResult();
    }
}
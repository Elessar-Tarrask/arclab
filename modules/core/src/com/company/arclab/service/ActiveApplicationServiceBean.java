package com.company.arclab.service;

import com.company.arclab.MyApplicationTaskDto;
import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.entity.UserGroup;
import com.haulmont.addon.bproc.query.TaskDataQuery;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import com.haulmont.addon.bproc.service.BprocTaskService;
import com.haulmont.addon.bproc.service.UserGroupService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(ActiveApplicationService.NAME)
public class ActiveApplicationServiceBean implements ActiveApplicationService {

    @Inject
    private DataManager dataManager;
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private Logger log;
    @Inject
    private BprocTaskService bprocTaskService;
    @Inject
    private UserGroupService userGroupService;

    @Override
    public List<TaskData> getActiveTasks(User user) {
        List<TaskData> userTasks = bprocTaskService.createTaskDataQuery()
                .taskAssignee(user)
                .active()
                .list();
        TaskDataQuery candidatesTaskDataQuery = bprocTaskService.createTaskDataQuery();
        if (!getUserGroupCodes(user).isEmpty()) {
            candidatesTaskDataQuery.taskCandidateGroupIn(getUserGroupCodes(user));
        }
        candidatesTaskDataQuery.taskCandidateUser(user);
        List<TaskData> groupTasks = candidatesTaskDataQuery
                .active()
                .list();
        return Stream.concat(userTasks.stream(), groupTasks.stream())
                .sorted(Comparator.comparing(TaskData::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserGroupCodes(User user) {
        List<UserGroup> userGroups = userGroupService.getUserGroups(user);
        return userGroups.stream()
                .map(UserGroup::getCode)
                .collect(Collectors.toList());
    }


    @Override
    public void publishTasksCount(User user) {

    }

    @Override
    public List<String> getTaskNameOptionsList(UUID userID) {
        User user = dataManager.load(User.class).id(userID).one();
        return getActiveTasks(user).stream().map(TaskData::getName).distinct().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<String> getApplicationTypeOptionsList(UUID userID) {
        User user = dataManager.load(User.class).id(userID).one();
        Set<String> collect = getActiveTasks(user).stream().map(TaskData::getProcessDefinitionId).collect(Collectors.toSet());
        if (collect.isEmpty())
            return new ArrayList<>();
        List<ProcessInstanceData> instanceDataList = bprocRuntimeService.createProcessInstanceDataQuery().processDefinitionIds(collect).list();
        return instanceDataList.stream().map(ProcessInstanceData::getProcessDefinitionName).distinct().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    // обратная совместимость для мобильного приложения
    public List<MyApplicationTaskDto> getUserApplicationTasks(UUID userID, String selectedIinBin, String selectedType, String selectedTask, Integer offset, Integer limit) {
        return getUserApplicationTasks(userID, selectedIinBin, selectedType, selectedTask, null, offset, limit);
    }

    @Override
    public List<MyApplicationTaskDto> getUserApplicationTasks(UUID userID, String selectedIinBin, String selectedType, String selectedTask, Long reqId, Integer offset, Integer limit) {
        User user = dataManager.load(User.class).id(userID).one();
        List<TaskData> userTasks = getActiveTasks(user);
        if (offset == null)
            offset = 0;
        if (limit == null)
            limit = userTasks.size();

        Map<String, String> dataTypeMap = getDataTypeMap(userTasks);
        // task name and task type loaded

        List<MyApplicationTaskDto> result = new ArrayList<>();

        for (int elem = limit + 1, ptr = 0; ptr < userTasks.size() && elem > 0; ptr++) {
            TaskData data = userTasks.get(ptr);
            //  для фильтра
            Filter filter = getFilter(selectedIinBin, selectedType, selectedTask, reqId, dataTypeMap, data);
            try {
                Application application = (Application) bprocRuntimeService.getVariable(data.getExecutionId(), "application");
                if (application != null)
                    application = dataManager.reload(application, "application-task");
                if (application != null && filter.pass(application)) {
                    if (offset > 0) offset--;
                    else {
                        MyApplicationTaskDto applicationTaskDto = getMyApplicationTaskDto(dataTypeMap, data, application, user);

                        result.add(applicationTaskDto);
                        elem--;
                    }
                }
            } catch (IllegalArgumentException | RemoteException e) {
                log.info(e.getMessage());
            }
        }

        return result;
    }

    private Map<String, String> getDataTypeMap(List<TaskData> userTasks) {
        Map<String, String> dataTypeMap = new HashMap<>();
        Set<String> collect = userTasks.stream()
                .map(TaskData::getProcessDefinitionId)
                .collect(Collectors.toSet());

        if (!collect.isEmpty()) {
            List<ProcessInstanceData> instanceDataList = bprocRuntimeService.createProcessInstanceDataQuery().processDefinitionIds(collect).list();
            instanceDataList.forEach(processInstanceData -> dataTypeMap.put(processInstanceData.getProcessDefinitionId(), processInstanceData.getProcessDefinitionName()));
        }
        return dataTypeMap;
    }

    private Filter getFilter(String selectedIinBin, String selectedType, String selectedTask, Long reqId, Map<String, String> dataTypeMap, TaskData data) {
        return application -> {
            if (selectedTask != null && !selectedTask.isEmpty()) {
                if (!(data != null && selectedTask.equals(data.getName())))
                    return false;
            }
            if (selectedType != null && !selectedType.isEmpty()) {
                if (data == null) return false;
                String procName = dataTypeMap.getOrDefault(data.getProcessDefinitionId(), null);
                if (!selectedType.equals(procName))
                    return false;
            }
            if (selectedIinBin != null && !selectedIinBin.isEmpty() && (application.getIinBin() == null || !application.getIinBin().contains(selectedIinBin)))
                return false;
            if (reqId != null && !reqId.equals(application.getReqId()))
                return false;
            return true;
        };
    }

    private MyApplicationTaskDto getMyApplicationTaskDto(
            Map<String, String> dataTypeMap, TaskData data, Application application, User assignee) {
        MyApplicationTaskDto applicationTaskDto = new MyApplicationTaskDto();
        applicationTaskDto.setExecutionId(UUID.fromString( data.getExecutionId()));
        applicationTaskDto.setTaskName(data.getName());
        applicationTaskDto.setTaskDate(data.getCreateTime());
        applicationTaskDto.setApplicationType(dataTypeMap.getOrDefault(data.getProcessDefinitionId(), ""));
        applicationTaskDto.setApplication(application);
        applicationTaskDto.setTaskData(data);
        applicationTaskDto.setAssignee(assignee);

        // if the task is active at least for 8 hours we have to mark it as alert...
        // which is used to highlight the task in the overall list of user tasks...
        Calendar now = Calendar.getInstance();
        Calendar startPlus8Hours = Calendar.getInstance();
        startPlus8Hours.setTime(data.getCreateTime());
        startPlus8Hours.add(Calendar.HOUR, 8);
        if (startPlus8Hours.before(now)) applicationTaskDto.setAlert(true);
        return applicationTaskDto;
    }

    @FunctionalInterface
    interface Filter {
        boolean pass(Application application);
    }
}
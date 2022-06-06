package com.company.arclab;

import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.cuba.security.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class MyApplicationTaskDto implements Serializable {
    private Application application;
    private String taskName;
    private Date taskDate;
    private String applicationType;
    private UUID executionId;
    private Boolean alert;
    private TaskData taskData;
    private User assignee;

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }

    public Boolean isAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String taskType) {
        this.applicationType = taskType;
    }

    public UUID getExecutionId() {
        return executionId;
    }

    public void setExecutionId(UUID executionId) {
        this.executionId = executionId;
    }
}


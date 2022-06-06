package com.company.arclab.entity.application;

import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.security.entity.User;

import java.util.Date;

@MetaClass(name = "jcrm_MyApplicationTaskItem")
@NamePattern("%s|taskName")
public class MyApplicationTaskItem extends BaseUuidEntity {
    // Класс используется для отображения в разделе Мои Задачи
    private static final long serialVersionUID = 5860301466385093719L;

    @MetaProperty
    private Application application;

    @MetaProperty
    private String taskName;

    @MetaProperty
    private Date taskDate;

    @MetaProperty
    private String applicationType;

    @MetaProperty
    private Boolean alert;

    @MetaProperty
    private TaskData taskData;

    @MetaProperty
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

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
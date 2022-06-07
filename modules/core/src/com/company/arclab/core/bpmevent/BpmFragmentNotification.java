package com.company.arclab.core.bpmevent;

import com.company.arclab.event.GlobalUserAssignedEvent;
import com.company.arclab.event.GlobalUserCompletedEvent;
import com.company.arclab.event.ProcessStateEvent;
import com.google.common.base.Strings;
import com.haulmont.addon.bproc.entity.ExecutionData;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.events.ActivityStartedEvent;
import com.haulmont.addon.bproc.events.ProcessCompletedEvent;
import com.haulmont.addon.bproc.events.UserTaskAssignedEvent;
import com.haulmont.addon.bproc.events.UserTaskCompletedEvent;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import com.haulmont.cuba.core.global.Events;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@Component(BpmFragmentNotification.NAME)
public class BpmFragmentNotification {
    public static final String NAME = "arclab_BpmFragmentNotification";
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private Events events;

    @EventListener
    protected void onActivityStarted(ActivityStartedEvent event) {

        ExecutionData executionData = bprocRuntimeService.createExecutionDataQuery()
                .executionId(event.getExecutionId())
                .singleResult();

        events.publish(new ProcessStateEvent(this, executionData, /*executionId,*/
                formatActivityName(event), event.getActivityType(), event.getActivityId()));
    }

    private String formatActivityName(ActivityStartedEvent event) {
        String activityName;
        if (!Strings.isNullOrEmpty(event.getActivityType())
                && event.getActivityType().contains("Gateway")
                && Strings.isNullOrEmpty(event.getActivityName())) {
            activityName = /*format.format(date) + " - */"Gateway";
        } else
            activityName = /*format.format(date) + " - " +*/ event.getActivityName();
        return activityName;
    }

    @EventListener
    protected void onTaskAssigned(UserTaskAssignedEvent event) {
        // Отправка уведомлений, при назначении заявки на пользовательский шаг, назначенным пользователям
        sendEmailToAssignee(event.getTaskData());

        events.publish(new GlobalUserAssignedEvent(this, event.getTaskData()));
    }

    @EventListener
    protected void onTaskCompleted(UserTaskCompletedEvent event) {
        events.publish(new GlobalUserCompletedEvent(this, event.getTaskData()));
    }

    @EventListener
    protected void onProcessCompleted(ProcessCompletedEvent event) {
        // TODO
    }

    private void sendEmailToAssignee(TaskData taskData) {
        // TODO
    }
}
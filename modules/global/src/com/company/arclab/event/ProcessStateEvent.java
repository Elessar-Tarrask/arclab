package com.company.arclab.event;

import com.haulmont.addon.bproc.entity.ExecutionData;
import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;

// событие, генерируемое в CRM при смене шагов в BPM процессе
// используется для отображения динамической информации на экранах в {@link BpmPanelCustomFragment#processEvent } */
public class ProcessStateEvent extends GlobalApplicationEvent implements GlobalUiEvent {

    private final ExecutionData executionData;
    //private String executionId;
    private final String activityName;
    private final String activityType;
    private final String activityId;

    public ProcessStateEvent(Object source, ExecutionData executionData, /*String executionId,*/
                             String activityName, String activityType, String activityId) {
        super(source);
        this.executionData = executionData;
        //this.executionId = executionId;
        this.activityName = activityName;
        this.activityType = activityType;
        this.activityId = activityId;
    }

    public ExecutionData getExecutionData() {
        return executionData;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

//    public String getExecutionId() {
//        return executionId;
//    }

    public String getActivityId() {
        return activityId;
    }
}

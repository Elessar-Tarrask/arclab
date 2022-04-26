package com.company.arclab.web.screens;

import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiController("arclab_BpmPanelCustomFragment")
@UiDescriptor("bpm-panel-custom-fragment.xml")
public class BpmPanelCustomFragment extends ScreenFragment {
    private Application application;


//    @EventListener
//    protected void processEvent(ProcessStateEvent event) {
//        if (application == null || event == null || event.getExecutionData() == null)
//            return;
//
////        ExecutionData activData = bprocRuntimeService.createExecutionDataQuery()
////                .executionId(event.getProcessInstanceData().getId()).singleResult();
////        if (activData == null)
////            activData = event.getProcessInstanceData();
//
////        fireEventAndUpdateApplication(activData, event);
////    }
////
////    private void fireEventAndUpdateApplication(ExecutionData activData, ProcessStateEvent event) {
//
//        // определяем, нужно ли обрабатывать данное событие, то есть наша ли это заявка?
//        boolean ourApp = false;
//
//        String eventPid = event.getExecutionData().getProcessInstanceId();
//        Object applicationObj = null;
//        try {
//            ProcessInstanceData pData = bprocRuntimeService
//                    .createProcessInstanceDataQuery()
//                    .processInstanceId(eventPid)
//                    .singleResult();
//            // эта проверка нужна чтобы не получать ошибку FlowableObjectNotFoundException, которая заполняет ВСЕ логи
//            if (pData != null)
//                // пытаемся получить из процесса переменную application процесса
//                applicationObj = bprocRuntimeService.getVariable(eventPid, "application");
//        } catch (RuntimeException ignored) {
//            // если возникло исключение FlowableObjectNotFoundException - значит процесс уже успел завершиться
//            // он уже успел завершиться потому что это были последние шаги процесса и они выполнились слишком быстро ))
//        }
//        // если нам удалось найти переменную application процесса - сравниваем по id из переменной
////            if (applicationObj != null) {
//        if (applicationObj instanceof Application) {
//            Application appFromProcVariable = (Application) applicationObj;
//            if (application.getId().equals(appFromProcVariable.getId())) {
//                ourApp = true;
////                    }
//            }
//        } else {
//            // если не удалось найти переменную процесса, то тогда сравниваем по id процесса
//            // соответственно данное условие не будет работать для подпроцессов
//            ourApp = (eventPid.equals(application.getProcId())
//                    || (subProcessInstance != null
//                    && eventPid.equals(subProcessInstance.getId())));
//        }
//
//
//        // если выяснили, что это наше событие - опубликуем его дальше
//        if (ourApp) {
//            checkSubProcessChange(event.getExecutionData());
//
//            if (event.getActivityType() != null
//                    && event.getActivityType().equalsIgnoreCase("serviceTask"))
//                enableProgress();
//            else
//                disableProgress();
//
//            try {
//
//                publishEventToParentScreen(event);
//
//                updateAllTabs(event);
//
//                // событие не изменилось
//                if (event.getActivityId() != null
//                        && event.getActivityId().equals(eventLastActivityId)) {
//                    onError(event);
//                }
//                eventLastActivityId = event.getActivityId();
//            } catch (Exception e) {
//                log.error("Error in BpmPanelCustomFragment.processEvent", e);
//            }
//        }
//
//    }
}
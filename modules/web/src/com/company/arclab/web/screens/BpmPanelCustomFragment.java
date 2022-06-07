package com.company.arclab.web.screens;

import com.company.arclab.entity.application.Application;
import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.event.ProcessStateEvent;
import com.company.arclab.event.ServiceStateEvent;
import com.company.arclab.service.ApplicationService;
import com.company.arclab.service.BPMNService;
import com.company.arclab.service.OfficerService;
import com.company.arclab.web.screens.application.ApplicationScreen;
import com.company.arclab.web.screens.identityapplication.IdentityApplicationEdit;
import com.haulmont.addon.bproc.entity.ExecutionData;
import com.haulmont.addon.bproc.entity.HistoricActivityInstanceData;
import com.haulmont.addon.bproc.entity.JobData;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.form.FormData;
import com.haulmont.addon.bproc.service.BprocFormService;
import com.haulmont.addon.bproc.service.BprocHistoricService;
import com.haulmont.addon.bproc.service.BprocManagementService;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import com.haulmont.addon.bproc.web.entities.model.FormType;
import com.haulmont.addon.bproc.web.processform.ProcessFormScreens;
import com.haulmont.addon.bproc.web.screens.processinstance.diagramfragment.BpmnDiagramViewerFragment;
import com.haulmont.addon.bproc.web.screens.processinstance.historyfragment.ProcessInstanceEditHistoryFragment;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.ProgressBar;
import com.haulmont.cuba.gui.components.RichTextArea;
import com.haulmont.cuba.gui.components.TabSheet;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiControllerUtils;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.company.arclab.entity.EApplicationStatus.*;

@UiController("arclab_BpmPanelCustomFragment")
@UiDescriptor("bpm-panel-custom-fragment.xml")
public class BpmPanelCustomFragment extends ScreenFragment {

    private Application application;
    private ApplicationScreen parentScreen;
    // это данные основного АКТИВНОГО процесса, заполняется в setBpmPanelParams, не может меняться
    private ProcessInstanceData processInstance;
    // это данные основного АКТИВНОГО ПОДпроцесса (первого попавшегося), может измениться
    private ProcessInstanceData subProcessInstance;
    private boolean taskOpened = false;
    private String eventLastActivityId = null;
    private String lastErrorShowed = null;

    @Inject
    private ButtonsPanel buttonsPanel;
    @Inject
    private VBoxLayout bpmDiagramTab;
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private BpmnDiagramViewerFragment bpmDiagramFragment;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private ProcessFormScreens processFormScreens;
    @Inject
    private Events events;
    @Inject
    private ProgressBar progress;
    @Inject
    private ProcessInstanceEditHistoryFragment bpmHsitoryFragment;
    @Inject
    private TextField<String> reqIdField;
    @Inject
    private TextField<String> managerField;
    @Inject
    private TextField<String> applicationStatusField;
    @Inject
    private DateField<Date> dateField;
    @Inject
    private Messages messages;
    @Inject
    private Button restartJobButton;
    @Inject
    private TextField<String> taskNameField;
    @Inject
    private TextField<String> taskDueDateField;
    @Inject
    private TextField<String> taskCreateTimeField;
    @Inject
    private TextField<String> taskAssigneeField;
    @Inject
    protected BprocManagementService bprocManagementService;
    @Inject
    private BprocHistoricService bprocHistoricService;
    @Inject
    private TextField<String> stepNameField;
    @Inject
    private Logger log;
    @Inject
    private BPMNService bPMNService;
    @Inject
    private Button mainProcessDiagramButton;
    @Inject
    private Button subProcessDiagramButton;
    @Inject
    private Button mainProcessHistoryButton;
    @Inject
    private Button subProcessHistoryButton;
    @Inject
    private VBoxLayout logTab;
    @Inject
    private GroupBoxLayout bpmFragmentBox;
    @Inject
    private Button bpmActionBtn;
    @Inject
    private RichTextArea rtArea;
    @Inject
    private TabSheet bpmTabSheet;
    @Inject
    private BprocFormService bprocFormService;
    @Inject
    private RichTextArea infoField;
    @Inject
    private ApplicationService applicationService;
    @Inject
    private OfficerService officerService;

    @Subscribe
    public void onInit(InitEvent event) {
        bpmFragmentBox.setSettingsEnabled(false);
    }

    @EventListener
    protected void processEvent(ProcessStateEvent event) {
        if (application == null || event == null || event.getExecutionData() == null)
            return;

        // определяем, нужно ли обрабатывать данное событие, то есть наша ли это заявка?
        boolean ourApp = false;

        String eventPid = event.getExecutionData().getProcessInstanceId();
        Object applicationObj = null;
        try {
            ProcessInstanceData pData = bprocRuntimeService
                    .createProcessInstanceDataQuery()
                    .processInstanceId(eventPid)
                    .singleResult();
            // эта проверка нужна чтобы не получать ошибку FlowableObjectNotFoundException, которая заполняет ВСЕ логи
            if (pData != null)
                // пытаемся получить из процесса переменную application процесса
                applicationObj = bprocRuntimeService.getVariable(eventPid, "application");
        } catch (RuntimeException ignored) {
            // если возникло исключение FlowableObjectNotFoundException - значит процесс уже успел завершиться
            // он уже успел завершиться потому что это были последние шаги процесса и они выполнились слишком быстро ))
        }
        // если нам удалось найти переменную application процесса - сравниваем по id из переменной
//            if (applicationObj != null) {
        if (applicationObj instanceof Application) {
            Application appFromProcVariable = (Application) applicationObj;
            if (application.getId().equals(appFromProcVariable.getId())) {
                ourApp = true;
//                    }
            }
        } else {
            // если не удалось найти переменную процесса, то тогда сравниваем по id процесса
            // соответственно данное условие не будет работать для подпроцессов
            ourApp = (eventPid.equals(application.getProcId())
                    || (subProcessInstance != null
                    && eventPid.equals(subProcessInstance.getId())));
        }


        // если выяснили, что это наше событие - опубликуем его дальше
        if (ourApp) {
            checkSubProcessChange(event.getExecutionData());

            if (event.getActivityType() != null
                    && event.getActivityType().equalsIgnoreCase("serviceTask"))
                enableProgress();
            else
                disableProgress();

            try {

                publishEventToParentScreen(event);

                updateAllTabs(event);

                // событие не изменилось
                if (event.getActivityId() != null
                        && event.getActivityId().equals(eventLastActivityId)) {
                    onError(event);
                }
                eventLastActivityId = event.getActivityId();
            } catch (Exception e) {
                log.error("Error in BpmPanelCustomFragment.processEvent", e);
            }
        }

    }

    // три ситуации по новому шагу:
    // 1. Основной процесс только что перешёл в подпроцесс
    // 2. Был подпроцесс, но он только что завершился
    // 3. Из одного подпроцесса попали в другой подпроцесс (бывает?)
    private void checkSubProcessChange(ExecutionData activData) {
        if (activData == null)
            return;

        // почему-то не совпадает id процесса из события с нашим текущим id процесса ?
        if (activData.getProcessInstanceId() != null
                && processInstance != null && processInstance.getId() != null
                && !activData.getProcessInstanceId().equals(processInstance.getId())) {

            // у нас нет продпроцесса, может появился? (ситуация №1)
            if (subProcessInstance == null)
                reassignSubProcess();
            else
                // подпроцесс есть, но событие даже не из текущего продпроцесса? (ситуация №3)
                if (!activData.getProcessInstanceId().equals(subProcessInstance.getId()))
                    reassignSubProcess();
        } else {
            // получается событие пришло из основного процесса, но у нас есть заполнный подпроцесс
            if (subProcessInstance != null)
                // может подпроцесс уже завершился, и его нужно обнулить (ситуация №2)
                reassignSubProcess();
        }
    }

    private void reassignSubProcess() {
        subProcessInstance = getSubprocessInstance(processInstance);
    }

    private void publishEventToParentScreen(ProcessStateEvent event) {
        if (application instanceof IdentityApplication)
            events.publish(new ServiceStateEvent(this, (IdentityApplication) application, messageBundle.getMessage("appUpdated")));
        else {
            if (parentScreen != null) {
                parentScreen.afterService(event);
            }
        }
    }

    private void onError(ProcessStateEvent event) {
        if (application.getInfo() != null) {

            if (!application.getInfo().equals(lastErrorShowed)) {
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption("Возникла ошибка в процессе")
                        .withDescription(getExceptionDescription(application.getInfo()))
                        .show();
                lastErrorShowed = application.getInfo();
            }
        }
        disableProgress();
        try {
            Thread.sleep(5000); // flawable немного не успевает, подождём другой поток
        } catch (InterruptedException e) {
            log.error("Error", e);
        }
        restartJobButton.setVisible(isRestartButtonVisible());
    }

    private boolean isRestartButtonVisible() {
        List<JobData> jobs = getJobsData();
        if (jobs == null
                || jobs.isEmpty()) {
            return false;
        }
        return true;
    }

    public void setBpmPanelParams(IdentityApplication app, String procDefKey) {
        setBpmPanelParams(null, app, procDefKey);
    }

    // инициализация
    public void setBpmPanelParams(ApplicationScreen parentScreen, Application app, String procDefKey) {
        if (parentScreen == null && !(app instanceof IdentityApplication))
            throw new RuntimeException("Некорректная инициализация фрагмента BpmPanelCustomFragment. parentScreen is null!");
        this.parentScreen = parentScreen;
        this.application = app;
        processInstance = bPMNService.getProcessInstanceByApp(application, false); // больше не должен измениться

        if (processInstance == null) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Процесс не найден.")
                    .withDescription("Активный процесс отсутствует.")
                    .show();
//            return;
        }

        reassignSubProcess();

//        if (isLogginTabVisible(procDefKey)) {
//            bpmTabSheet.getTab("logTab").setVisible(true);
//            screenUtils.addLogFragment(this, logTab, application.getId());
//        } else
//            bpmTabSheet.getTab("logTab").setVisible(false);

        updateAllTabs(null);

        // видны ли кнопки Обновить, Открыть окно задачи БП недоступны
        buttonsPanel.setVisible(isButtonsVisible(procDefKey));

        // эти данные не будут изменены
        reqIdField.setValue(String.valueOf(application.getReqId()));
        managerField.setValue(application.getManager().getName());
        dateField.setValue(application.getCreateTs());
//        applicationStatusField.setValue(getStatusLocalized(application));
//        infoField.setVisible(application.getInfo() != null);
//        infoField.setValue(application.getInfo());
//        infoField.setDescription(getExceptionDescription(application.getInfo()));

//        if (application.getProcId() != null)
//            initDiagram(application.getProcId());
//        else
//        initDiagram(processInstance); // лишний вызов, есть в updateTaskInfo()
    }

    private boolean isLogginTabVisible(String procDefKey) {
        // для данных процессов есть вкладка Логирование
//        switch (procDefKey) {
//            case CurrentAccountService.PROC_DEFINITION_KEY:
//            case DepositOpenService.PROC_DEFINITION_KEY:
//            case CorpCardsBpmService.CORP_CARD_PROC_DEFINITION_KEY:
//            case TCorpCardApplication.CORP_CARD_ACQUIRING:
//                return true;
//        }
        return false;
    }

    private void updateAllTabs(ProcessStateEvent event) {
        updateTaskInfo(event); // вкладка Текущая задача
        updateDiagramTab();   // вкладка Схема БП
        updateHistoricActivityInstance(event); // Журнал
        resetHistoryFragmentParams(); // История
        updateCommentsContainer(); // Комментарии

        setBpmFragmentCaption();
    }

    private void updateDiagramTab() {
        try {
            mainProcessDiagramButton.setVisible(subProcessInstance != null);
            subProcessDiagramButton.setVisible(subProcessInstance != null);
            if (processInstance == null)
                return;
            ProcessInstanceData instanceToShow = processInstance;
            if (subProcessInstance != null)
                instanceToShow = subProcessInstance;
            initDiagram(instanceToShow);
        } catch (Throwable logOnly) {
            log.error("Error", logOnly);
        }
    }

    private ProcessInstanceData getSubprocessInstance(ProcessInstanceData processInstance) {
        /**
         * Если вдруг активировался подпроцесс
         */
        if (processInstance == null)
            return null;

        return subProcessInstance = getSubProcessInstanceData(processInstance.getId());
    }

    private boolean isButtonsVisible(String procDefKey) {
        if (processInstance == null) // для завершённых процессов не отображать кнопки
            return false;
        // для данных процессов кнопки Обновить, Открыть окно задачи БП недоступны
        switch (procDefKey) {
//            case CurrentAccountService.PROC_DEFINITION_KEY:
//            case DepositOpenService.PROC_DEFINITION_KEY:
//            case CorpCardsBpmService.CORP_CARD_PROC_DEFINITION_KEY:
//            case TCorpCardApplication.CORP_CARD_ACQUIRING:
//            case SibService.PROC_DEFINITION_KEY:
//            case EndToEndService.PROC_DEFINITION_KEY:
            case "businessLightOpenGLA":
            case "kainarOpenGLA":
            case "AmlCheckProcess":
                //case "CC": 22/07/2021 нужны кнопки
            case "creditProcessSendingDossier":
            case "creditProcessExpertise":
            case "sendingCreditDossier":
                return false;
        }
        return true;
    }

    private String getExceptionDescription(String info) {
        String additionalBasicInfo = "Пожалуйста перезапустите шаг, а при повторении ошибки обратитесь в службу поддержки \n\n";
        if (StringUtils.isEmpty(info))
            return null;
        String cause = StringUtils.substringBetween(info, "Caused by:", "at ");
        if (cause == null)
            return null;
        String details = StringUtils.substringAfter(cause, "---");
        if (details == null)
            return additionalBasicInfo + " " + cause;
        return additionalBasicInfo + " " + details;
    }

    private String getStatusLocalized(Application application) {
        return application.getApplicationStatus() == null ? "" :
                messages.getMessage("com.company.arclab.entity.messages", "EApplicationStatus." + application.getApplicationStatus().getId());
    }

   /*
   //Общий метод вынесен в BPMNServiceBean - getProcessInstanceByApp(Application application)
   private ProcessInstanceData getProcInstance(Application application) {
        ProcessInstanceData processInstanceLocal = null;

        List<ProcessInstanceData> processInstances = bprocRuntimeService.createProcessInstanceDataQuery()
//                .processDefinitionKey(procDefKey) // ищем подпроцессы
                .variableValueEquals("application", application)
                .list();
        processInstances.sort((c1, c2) -> c1.getStartTime().compareTo(c2.getStartTime()));
        if (processInstances.size() > 0)
            processInstanceLocal = processInstances.get(0);
        else {
            // не нашли процесс по заявке? поищем по id процесса
            if (application.getProcId() != null) {
                processInstances = bprocRuntimeService.createProcessInstanceDataQuery()
                        .processInstanceId(application.getProcId())
                        .list();
                if (processInstances.size() > 0)
                    processInstanceLocal = processInstances.get(0);
            }
        }

        return processInstanceLocal;
    }*/

    private ProcessInstanceData getSubProcessInstanceData(String processId) {
        return bprocRuntimeService.createProcessInstanceDataQuery()
                .superProcessInstanceId(processId)
                .active().singleResult();
    }

    private void resetHistoryFragmentParams() {
        try {
            mainProcessHistoryButton.setVisible(subProcessInstance != null);
            subProcessHistoryButton.setVisible(subProcessInstance != null);
            if (processInstance == null)
                return;
            ProcessInstanceData instanceToShow = processInstance;
            if (subProcessInstance != null)
                instanceToShow = subProcessInstance;

            bpmHsitoryFragment.setProcessInstanceData(instanceToShow); // может пальнуть ClassNotFoundException
        } catch (Throwable logOnly) {
            log.error("Error", logOnly);
        }

    }

    private void updateTaskInfo(ProcessStateEvent event) {
        TaskData task = getCurrentTask();

        try {
            bpmActionBtn.setEnabled(task != null); // кнопка Открыть окно задачи БП доступна при наличии хотя бы одного активного задания
            if (task != null) {
                // есть активное задание

                String description = bPMNService.getTaskDescription(task);
                rtArea.setValue(description);
                rtArea.setVisible(!StringUtils.isEmpty(rtArea.getValue()));

                taskNameField.setValue(task.getName());
                if (task.getCreateTime() != null)
                    taskCreateTimeField.setValue(getTaskCreateDate(task));
                if (task.getDueDate() != null) {
                    taskDueDateField.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(task.getDueDate()));
                    taskDueDateField.setVisible(true);
                } else {
                    taskDueDateField.setVisible(false);
                }
                taskAssigneeField.setValue(getAssigneeName(task.getAssignee()));

                taskCreateTimeField.setVisible(true);
                taskAssigneeField.setVisible(true);

                stepNameField.setVisible(false);
                restartJobButton.setVisible(false);
            } else {
                rtArea.setVisible(false);
                taskNameField.setValue("Нет активной задачи");
                taskCreateTimeField.setValue(null);
                taskDueDateField.setValue(null);
                taskAssigneeField.setValue(null);

                taskCreateTimeField.setVisible(false);
                taskDueDateField.setVisible(false);
                taskAssigneeField.setVisible(false);

                if (event != null && event.getActivityName() != null) {
//                    String activityName = StringUtils.substringAfter(event.getActivityOrTaskName(), "- ");
                    String activityName = event.getActivityName();
                    stepNameField.setValue(activityName);
                    stepNameField.setVisible(activityName != null);
                } else {
                    // инициализация скрина - TODO - вывести название шага в поле stepNameField
//                List<ExecutionData> executions = bprocRuntimeService.createExecutionDataQuery()
//                        .processInstanceId(processInstance.getId())
//                        .list();

                }

                // есть ли активные задачи, но не на текущего пользователя??
                TaskData otherActiveTask = getActiveTaskAnyUser();
                if (otherActiveTask != null) {
                    rtArea.setVisible(true);
                    StringBuilder sb = new StringBuilder();
                    sb.append("<p>").append(getAssigneeName(otherActiveTask.getAssignee()));//.append("</p>");
                    sb.append(" выполняет задание </p>");
                    sb.append("<p>").append(otherActiveTask.getName()).append("</p>");
                    sb.append("<p> c ").append(getTaskCreateDate(otherActiveTask)).append("</p>");
                    rtArea.setValue(sb.toString());
                }
            }
        } catch (Throwable logOnly) {
            log.error("Error", logOnly);
        }

        if (application != null) {
            // перечитываем статус из БД
            try {
                Application app = dataManager.load(Application.class)
                        .view("application-view").id(application.getId())
                        .optional().orElse(null);
                if (app != null) {
                    if (app.getApplicationStatus() != null
                            && !app.getApplicationStatus().equals(application.getApplicationStatus()))
                        //application.setApplicationStatus(app.getApplicationStatus());
                        if (app.getInfo() != null
                                && !app.getInfo().equals(application.getInfo()))
                            //application.setInfo(app.getInfo());

                            if (application.getInfo() != null && processInstance != null && task == null)
                                restartJobButton.setVisible(isRestartButtonVisible());

                    if (FINISHED.equals(app.getApplicationStatus())
                            || CANCELED.equals(app.getApplicationStatus())
                            || REJECTED.equals(app.getApplicationStatus())) {
                        disableProgress();
                        restartJobButton.setVisible(false);
                    }

                    String additionalBasicInfo = "<span style=\"color:red\"><strong>Пожалуйста перезапустите шаг, а при повторении ошибки обратитесь в Техническую поддержку JCRM (Электронная почта <a href=\"mailto:CRMsupport@jusan.kz\">CRMsupport@jusan.kz</a> ,контактный телефон 500-4555)</strong></span> <br><br>";
                    applicationStatusField.setValue(getStatusLocalized(app));
                    infoField.setHtmlSanitizerEnabled(true);
                    infoField.setVisible(app.getInfo() != null);
                    if (app.getInfo() != null)
                        setInfoFieldError(app.getInfo());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
//       для корп карт, депозитов, текущего счета и кайнар не требуется открытия скрина
        // Откроет ТОЛЬКО CUBA-SCREEN!
//        handleActiveTaskScreenOpen(task); // удалил вызов, перенёс в onAfterShow - потому что возникает раньше, в onBeforeShow

    }

    public void setInfoFieldError(String appInfo) {
        String additionalBasicInfo = "<span style=\"color:red\"><strong>Пожалуйста перезапустите шаг, а при повторении ошибки обратитесь в Техническую поддержку JCRM (Электронная почта <a href=\"mailto:CRMsupport@jusan.kz\">CRMsupport@jusan.kz</a> ,контактный телефон 500-4555)</strong></span> <br><br>";
        infoField.setHtmlSanitizerEnabled(true);
        infoField.setVisible(appInfo != null);
        infoField.setValue(additionalBasicInfo + appInfo);
        infoField.setDescription(getExceptionDescription(appInfo));
    }

    private String getTaskCreateDate(TaskData task) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(task.getCreateTime());
    }

    // есть ли активные задачи, но не на текущего пользователя??
    private TaskData getActiveTaskAnyUser() {
        if (processInstance == null)
            return null;
        return applicationService.getActiveTaskAnyUser(processInstance.getId());
    }

    // есть ли активные задачи, но не на текущего пользователя??
//    private List<TaskData> getActiveTasksByProcessAnyUser(ProcessInstanceData pi) {
//        if (pi == null)
//            return null;
//        return bprocTaskService.createTaskDataQuery()
////                .taskAssignee(userSession.getCurrentOrSubstitutedUser())
//                .processInstanceId(pi.getId())
//                .active()
//                .orderByTaskCreateTime()
//                .desc()
//                .list();
//    }

    private TaskData getCurrentTask() {
        if (processInstance == null)
            return null;
        return applicationService.getCurrentTask(processInstance.getId());
//        TaskData task = null;
//        try {
//            List<TaskData> tasks = getActiveTasksByProcess(processInstance);
//            if (subProcessInstance != null)
//                tasks.addAll(getActiveTasksByProcess(subProcessInstance));
//            // выполнять будем первую активную задачу
//            if (!tasks.isEmpty()) {
//                task = tasks.get(0);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return task;
    }

//    private List<TaskData> getActiveTasksByProcess(ProcessInstanceData pi) {
//        if (pi == null)
//            return null;
//        return bprocTaskService.createTaskDataQuery()
//                .taskAssignee(userSession.getCurrentOrSubstitutedUser())
//                .processInstanceId(pi.getId())
//                .active()
//                .orderByTaskCreateTime()
//                .desc()
//                .list();
//    }


//    private void notifyProcessFinished(Application application) {
//        List<ProcessInstanceData> pids = bprocRuntimeService.createProcessInstanceDataQuery()
//                .active()
//                .variableValueEquals("application", application)
//                .list();
//        ProcessInstanceData pid = null;
//        // список может быть для подпроцессов
//        if (pids != null && !pids.isEmpty()) {
//            pid = pids.get(0);
//        }
//        if (pid == null)
//            notifications.create().withType(Notifications.NotificationType.WARNING)
//                    .withCaption(messageBundle.getMessage("warnProcFinished")).show();
//    }

    private String getAssigneeName(String assignee) {
        if (assignee == null)
            return "";
        User user = officerService.getUserById(UUID.fromString(assignee));
        if (user == null)
            return "";
        if (user.getName() != null)
            return user.getName();
        return user.getLogin();
    }

//    private void initDiagram(String processInstanceId) {
//        ProcessInstanceData processInstance = bprocRuntimeService.createProcessInstanceDataQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//        initDiagram(processInstance);
//    }

    // диаграмма отображается для подпроцесса, если он есть
    private void initDiagram(ProcessInstanceData instanceToShow) {
        bpmDiagramTab.setHeight("500px");
        bpmDiagramFragment.setProcessInstanceData(instanceToShow);
        bpmDiagramFragment.getFragment().setHeight("500px");
    }

    private void updateHistoricActivityInstance(ProcessStateEvent event) {

        if (application.getProcId() == null)
            return;

        try {
            List<HistoricActivityInstanceData> activities = getHistoricActivity(application.getProcId());

            if (subProcessInstance != null) {
                List<HistoricActivityInstanceData> subActivities = getHistoricActivity(subProcessInstance.getId());
                activities.addAll(subActivities);
            }

            activities.sort(Comparator.comparing(HistoricActivityInstanceData::getStartTime).reversed());

            // попробуем добавить текущий активный шаг - пробная версия
//            if (event != null && event.getActivityName() != null) {
//                HistoricActivityInstanceData data = new HistoricActivityInstanceData();
////                data.setActivityName(StringUtils.substringAfter(event.getActivityOrTaskName(), "- "));
//                data.setActivityName(event.getActivityName());
//                data.setStartTime(new Date());
//                activities.add(data);
//            }

            //historicActivityInstanceDatasDc.setItems(activities);
        } catch (Throwable logOnly) {
            log.error("Error", logOnly);
        }
    }

    private List<HistoricActivityInstanceData> getHistoricActivity(String procId) {
        // история шагов
        List<HistoricActivityInstanceData> historicActivities = bprocHistoricService.createHistoricActivityInstanceDataQuery()
                .processInstanceId(procId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();

        // убираем шаги без названия и переходы
        return historicActivities.stream()
                .filter(o -> o.getActivityName() != null
                        && !"sequenceFlow".equals(o.getActivityType())
                        && !"parallelGateway".equals(o.getActivityType()) // не отображать шлюзы
                        && !"exclusiveGateway".equals(o.getActivityType())// не отображать шлюзы
                )
                .collect(Collectors.toList());
    }

    private void disableProgress() {
        progress.setVisible(false);
    }

    private void enableProgress() {
        progress.setVisible(true);
    }

    private List<JobData> getJobsData() {
        if (processInstance == null)
            return null;
        List<JobData> jobs = null;
        try {
            jobs = bprocManagementService.createDeadLetterJobDataQuery()
                    .processInstanceId(processInstance.getId())
                    .list();
            if ((jobs == null
                    || jobs.isEmpty())
                    && subProcessInstance != null) {
                // если не нашли сломанных шагов, но есть подпроцесс - поищем там
                jobs = bprocManagementService.createDeadLetterJobDataQuery()
                        .processInstanceId(subProcessInstance.getId())
                        .list();
            }
        } catch (Throwable t) {
            log.error("getJobsData: Ошибка при получении списка шагов с ошибками", t);
        }
        return jobs;
    }

    @Subscribe("restartJobButton")
    public void onRestartJobButtonClick(Button.ClickEvent event) {
        if (processInstance == null) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Невозможно перезапустить шаг")
                    .withDescription("Процесс не найден")
                    .show();
            return;
        }

        List<JobData> jobs = getJobsData();

        if (jobs == null
                || jobs.isEmpty()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Невозможно перезапустить шаг")
                    .withDescription("Нет шага, доступного для перезапуска")
                    .show();
            return;
        }

        //application.setInfo(null); // TODO коммит в БД
        infoField.setValue(null);
        infoField.setDescription(null);

        bprocManagementService.moveDeadLetterJobToExecutableJob(jobs.get(0).getId());

        application.setInfo(null);
        UiControllerUtils.getScreenData(parentScreen).getDataContext().commit();

        restartJobButton.setVisible(false);

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Шаг перезапущен")
//                .withDescription("Нет шага, доступного для перезапуска")
                .show();
    }

    private void updateCommentsContainer() {
        if (application == null)
            return;
        try {
            //List<ProcessComment> comments = bPMNService.getApplicationComments(application);
            //processCommentsDc.getMutableItems().clear();
            //processCommentsDc.getMutableItems().addAll(comments);
        } catch (Throwable logOnly) {
            log.error("Error", logOnly);
        }
    }

    @Subscribe("refreshBtn")
    public void onRefreshBtnClick(Button.ClickEvent event) {

        updateAllTabs(null);
    }

    @Subscribe("mainProcessDiagramButton")
    public void onMainProcessDiagramButtonClick(Button.ClickEvent event) {
        initDiagram(processInstance);
    }

    @Subscribe("subProcessDiagramButton")
    public void onSubProcessDiagramButtonClick(Button.ClickEvent event) {
        initDiagram(subProcessInstance);
    }

    @Subscribe("mainProcessHistoryButton")
    public void onMainProcessHistoryButtonClick(Button.ClickEvent event) {
        bpmHsitoryFragment.setProcessInstanceData(processInstance);
    }

    @Subscribe("subProcessHistoryButton")
    public void onSubProcessHistoryButtonClick(Button.ClickEvent event) {
        bpmHsitoryFragment.setProcessInstanceData(subProcessInstance);
    }

    private void setBpmFragmentCaption() {
        if (application == null)
            return;
        StringBuilder builder = new StringBuilder();
        builder.append("Заявка №").append(application.getReqId());
        builder.append(", ").append(getStatusLocalized(application));

        if (!"Нет активной задачи".equals(taskNameField.getValue())) {
            builder.append(", Активная  задача: ").append(taskNameField.getValue());
        } else {
            TaskData otherActiveTask = getActiveTaskAnyUser();
            if (otherActiveTask != null) {
                String name = getAssigneeName(otherActiveTask.getAssignee());
                if (name != null)
                    builder.append(", в работе у ").append(name);
                else
                    builder.append(", в работе у ").append(otherActiveTask.getAssignee());
            }
        }

        boolean jobRestartJob = isRestartButtonVisible();

        if (jobRestartJob){
            builder.append(", Ошибка!");
        }
        restartJobButton.setVisible(jobRestartJob);

        bpmFragmentBox.setCaption(builder.toString());
    }

    @Subscribe("bpmActionBtn")
    public void onBpmActionBtnClick(Button.ClickEvent event) {
        if (application == null)
            return;
        if (processInstance == null) // процесс Завершён
            return;

        TaskData task = getCurrentTask();
        // если процесс реализован без использования без "окон задачи" - пропускаем
        // и если не CUBA-Screen..  22/07/2021
        if (!isButtonsVisible(processInstance.getProcessDefinitionKey()) && !isCubaScreen(task))
            return;

        if (task == null && event != null) // если вызвали из onAfterShow, то сообщение не показываем
            notifications.create().withType(Notifications.NotificationType.WARNING)
                    .withDescription("У Вас нет активной задачи по заявке")
                    .withCaption("")
                    .show();
        handleActiveTaskScreenOpen(task);
    }

//    private void showBpmTaskForm(TaskData task) { // ClientApplication clientApplication,
//        if (task != null && !taskOpened) {
//            Screen bpmTaskForm = processFormScreens.createTaskProcessForm(task, this);
//            taskOpened = true;
//            bpmTaskForm.addAfterCloseListener(e -> {
//                taskOpened = false;
//                publishEventToParentScreen(null);
//                updateAllTabs(null);
//            });
//            if (bpmTaskForm instanceof DynamicTaskProcessForm)
//                bpmTaskForm.getWindow().getDialogOptions().setWidth(800).setHeight(450);
//            else
//                bpmTaskForm.getWindow().getDialogOptions().setMaximized(true);
//            bpmTaskForm.show();
//        }
//    }

    /**
     * Открывает только CUBA-Screen
     *
     * @param taskData
     */
    private void handleActiveTaskScreenOpen(TaskData taskData) {
        if (taskData != null && isCubaScreen(taskData)) {
            Screen bpmTaskForm = processFormScreens.createTaskProcessForm(taskData, this);
            bpmTaskForm.getWindow().getDialogOptions().setMaximized(true);
            bpmTaskForm.addAfterCloseListener(e -> taskOpened = false);
            if (!taskOpened) {
                taskOpened = true;
                bpmTaskForm.show();
                try{
                    ((IdentityApplicationEdit)getHostController()).updateECPListTable();
                }catch (Exception err){
                    log.error(err.getLocalizedMessage());
                }
            }
        } else if (taskData != null && !isCubaScreen(taskData)) {
            Screen bpmTaskForm = processFormScreens.createTaskProcessForm(taskData, this);
            bpmTaskForm.addAfterCloseListener(e -> taskOpened = false);
            if (!taskOpened) {
                taskOpened = true;
                bpmTaskForm.show();
            }
        }
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    public void onAfterShow(Screen.AfterShowEvent event) {
        onBpmActionBtnClick(null);
    }


    private boolean isCubaScreen(TaskData taskData) {
        if (taskData != null) {
            FormData formData = bprocFormService.getTaskFormData(taskData.getId());
            return formData != null && formData.getType() != null
                    && formData.getType().equalsIgnoreCase(FormType.CUBA_SCREEN.getId());
        }
        return false;
    }

}
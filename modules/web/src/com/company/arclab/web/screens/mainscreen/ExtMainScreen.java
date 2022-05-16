package com.company.arclab.web.screens.mainscreen;

import com.company.arclab.entity.client.TManager;
import com.company.arclab.event.GlobalUserAssignedEvent;
import com.company.arclab.event.GlobalUserCompletedEvent;
import com.company.arclab.event.TaskNotificationEvent;
import com.company.arclab.web.screens.tmanager.TManagerProfile;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.entity.UserGroup;
import com.haulmont.addon.bproc.query.TaskDataQuery;
import com.haulmont.addon.bproc.service.BprocTaskService;
import com.haulmont.addon.bproc.service.UserGroupService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.main.MainScreen;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {

    @Inject
    private BprocTaskService bprocTaskService;
    @Inject
    private UserSession userSession;

    private List<String> userGroupCodes;
    @Inject
    private UserGroupService userGroupService;
    @Inject
    private SideMenu sideMenu;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        SideMenu.MenuItem tasks = sideMenu.getMenuItem("tasks");
        if (tasks == null) return;
        tasks.setBadgeText(String.valueOf(getTotalMyTasks()));
    }

    protected long getTotalMyTasks() {
        long assigneeCount = bprocTaskService.createTaskDataQuery()
                .taskAssignee(userSession.getCurrentOrSubstitutedUser())
                .active()
                .count();
        TaskDataQuery candidatesTaskDataQuery = bprocTaskService.createTaskDataQuery();
        if (!getUserGroupCodes().isEmpty()) {
            candidatesTaskDataQuery.taskCandidateGroupIn(getUserGroupCodes());
        }
        candidatesTaskDataQuery.taskCandidateUser(userSession.getCurrentOrSubstitutedUser());
        return assigneeCount + candidatesTaskDataQuery.active().count();
    }

    protected List<String> getUserGroupCodes() {
        if (userGroupCodes == null) {
            List<UserGroup> userGroups = userGroupService.getUserGroups(userSession.getCurrentOrSubstitutedUser());
            userGroupCodes = userGroups.stream()
                    .map(UserGroup::getCode)
                    .collect(Collectors.toList());
        }
        return userGroupCodes;
    }

    @Subscribe("profileButton")
    public void onProfileButtonClick(Button.ClickEvent event) {
        screenBuilders.editor(TManager.class, this)
                .newEntity()
                .withOpenMode(OpenMode.NEW_TAB)
                .withScreenClass(TManagerProfile.class)
                .build().show();
    }

    @EventListener
    public void incrementTasks(GlobalUserAssignedEvent event) {
        try {
            String uid = userSession.getCurrentOrSubstitutedUser().getId().toString();
            TaskData data = event.getData();
            if (data == null ||
                    (data.getAssignee() != null && !data.getAssignee().equals(uid))) return;
            log.info("Incrementing... " + data.getAssignee());
            SideMenu.MenuItem item = sideMenu.getMenuItem("tasks");
            if (item == null) return;
            item.setBadgeText(String.valueOf(Integer.parseInt(item.getBadgeText()) + 1));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @EventListener
    public void taskNotificationEvents(TaskNotificationEvent event) {
        if(event != null && event.getGeneralTaskCount() != null
                && event.getGeneralTaskCount() > 0 && event.getUser() != null
                && event.getUser().equals(userSession.getCurrentOrSubstitutedUser())){
            String description = "Общее количество - " + event.getGeneralTaskCount() +
                    (event.getCreditCommitteeCount() > 0 ? " \n" + "Заявки Кредитного комитета - " + event.getCreditCommitteeCount() : "");
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("У Вас есть задачи на согласование - Мои задачи")
                    .withDescription(description)
                    .withHideDelayMs(7000)
                    .show();
        }
    }

    @EventListener
    public void decrementTasks(GlobalUserCompletedEvent event) {
        try {
            String uid = userSession.getCurrentOrSubstitutedUser().getId().toString();
            TaskData data = event.getData();
            if (data == null ||
                    (data.getAssignee() != null && !data.getAssignee().equals(uid))) return;
            log.info("Decrementing... " + data.getAssignee());
            SideMenu.MenuItem item = sideMenu.getMenuItem("tasks");
            if (item == null) return;
            item.setBadgeText(String.valueOf(Integer.parseInt(item.getBadgeText()) - 1));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
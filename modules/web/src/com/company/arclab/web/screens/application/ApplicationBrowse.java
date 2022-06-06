package com.company.arclab.web.screens.application;

import com.company.arclab.MyApplicationTaskDto;
import com.company.arclab.entity.application.MyApplicationTaskItem;
import com.company.arclab.service.ActiveApplicationService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.Application;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UiController("jcrm_Application.browse")
@UiDescriptor("application-browse.xml")
@LookupComponent("applicationsTable")
@LoadDataBeforeShow
public class ApplicationBrowse extends StandardLookup<Application> {

    private static final int limit = 50;
    protected List<MyApplicationTaskItem> loadedApplications = new ArrayList<>();
    protected int offset = 0;
    protected int pageNum = 1;
    protected int ptr = 0;
    protected String selectedTask;
    protected String selectedType;
    protected String selectedIinBin;
    protected Map<UUID, MyApplicationTaskDto> dataMap = new HashMap<>();

    @Inject
    private UserSession userSession;
    @Inject
    private LookupField<String> task;
    @Inject
    private TextField<String> iinBin;
    @Inject
    private TextField<Long> reqIdField;
    @Inject
    private LookupField<String> type;
    @Inject
    private ActiveApplicationService activeApplicationService;
    @Inject
    private Button prev;
    @Inject
    private Button next;
    @Inject
    private Label<String> page;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionContainer<MyApplicationTaskItem> myApplicationTaskItemDc;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        UUID userID = userSession.getCurrentOrSubstitutedUser().getId();
        task.setOptionsList(activeApplicationService.getTaskNameOptionsList(userID));
        type.setOptionsList(activeApplicationService.getApplicationTypeOptionsList(userID));
//        loadApplications(); // вызов есть в loadApplications
//        initTaskTableGeneratedColumns();
        btnClick();
    }

    protected void reset() {
        offset = 0;
        pageNum = 1;
        ptr = 0;
    }
    protected void loadApplications() {
        loadedApplications.clear();
        List<MyApplicationTaskDto> applicationTasks = activeApplicationService.getUserApplicationTasks(userSession.getCurrentOrSubstitutedUser().getId(),
                selectedIinBin, selectedType, selectedTask, reqIdField.getValue(),  offset, limit);
        applicationTasks.forEach(obj -> {
            dataMap.put(obj.getApplication().getId(), obj);
            loadedApplications.add(createTaskItem(obj));
        });
        List<MyApplicationTaskItem> dcElements = loadedApplications;
        if(loadedApplications.size() > limit)
            dcElements = loadedApplications.subList(0, loadedApplications.size() - 1);
        myApplicationTaskItemDc.setItems(dcElements);
    }

    private MyApplicationTaskItem createTaskItem(MyApplicationTaskDto taskDto) {
        if (taskDto == null)
            return null;
        MyApplicationTaskItem newItem = dataManager.create(MyApplicationTaskItem.class);
        newItem.setId(taskDto.getExecutionId());
        newItem.setTaskDate(taskDto.getTaskDate());
        newItem.setTaskName(taskDto.getTaskName());
        newItem.setApplicationType(taskDto.getApplicationType());
        newItem.setApplication(taskDto.getApplication());
        newItem.setAlert(taskDto.isAlert());
        return newItem;
    }

    void btnClick() {
        loadApplications();
        boolean prevFlag = true, nextFlag = false;
        if(pageNum == 1) prevFlag = false;
        if(limit < loadedApplications.size()) nextFlag = true;
        prev.setVisible(prevFlag);
        next.setVisible(nextFlag);
        page.setValue("Страница " + pageNum);
    }

    @Subscribe("prev")
    public void onPrevClick(Button.ClickEvent event) {
        pageNum--;
        offset -= limit;
        btnClick();
    }

    @Subscribe("next")
    public void onNextClick(Button.ClickEvent event) {
        pageNum++;
        offset += limit;
        if(offset + limit > loadedApplications.size()) loadApplications();
        btnClick();
    }

    @Subscribe("search")
    public void onSearchClick(Button.ClickEvent event) {
        selectedTask = task.getValue();
        selectedType = type.getValue();
        selectedIinBin = iinBin.getRawValue();
        reset();
        btnClick();
    }

    @Subscribe("applicationsTable.edit")
    public void onApplicationsTableEdit(Action.ActionPerformedEvent event) {
        EditAction<MyApplicationTaskItem> action = (EditAction<MyApplicationTaskItem>)event.getSource();
        MyApplicationTaskItem item = (MyApplicationTaskItem)action.getTarget().getSelected().stream()
                .findFirst().orElse(null);
        if (item != null) {
            screenBuilders.editor(Application.class, this)
//                    .withScreenClass(TClientAuthPersonEdit.class)
                    .editEntity(item.getApplication())
                    .build()
                    .show();
        }
    }
}
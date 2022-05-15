package com.company.arclab.web.screens.application;

import com.company.arclab.entity.application.Application;
import com.company.arclab.event.ProcessStateEvent;
import com.company.arclab.web.screens.BpmPanelCustomFragment;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import org.slf4j.Logger;

import javax.inject.Inject;

// все скрины заявок, которые хотят получасть событие ProcessStateEvent должны наследоваться от этого класса
public abstract class ApplicationScreen<T extends Application> extends StandardEditor<T> {

    @Inject
    private BpmPanelCustomFragment bpmPanelFragment;
    @Inject
    protected Notifications notifications;
    @Inject
    protected Logger log;
    @Inject
    protected InstanceContainer<T> applicationDc;

    abstract public void afterService(ProcessStateEvent event);
    abstract protected void reloadEntity() ;
    abstract protected String getProcessDefKey() ;

    protected T getApplication() {
        return getEditedEntity();
//        return getEditedEntityContainer().getItemOrNull() == null ? getEditedEntity() : getEditedEntityContainer().getItem();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        this.getWindow().setCaption(this.getWindow().getCaption() + " №" + getApplication().getReqId());
    }

    @Subscribe
    // необходимо выполнить reloadEntity до вызова метода setBpmParams
    public void onAfterShow(AfterShowEvent event) {
        if(PersistenceHelper.isNew(getEditedEntity())) {
            closeWithDiscard();
            return;
        }

        setBpmParams();
    }

    private void setBpmParams() {
        try {
            bpmPanelFragment.setBpmPanelParams(this, getApplication(), getProcessDefKey());
        } catch (Exception e) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка при подгрузке данных формы")
                    .withDescription(e.getMessage())
                    .show();
            log.error("Error", e);
        }
    }
}

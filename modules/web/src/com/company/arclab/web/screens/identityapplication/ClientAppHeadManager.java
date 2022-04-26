package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.TManager;
import com.company.arclab.service.BPMNService;
import com.google.common.base.Strings;
import com.haulmont.addon.bproc.web.processform.Outcome;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("arclab_ClientAppHeadManager")
@UiDescriptor("client-app-head-manager.xml")
@EditedEntityContainer("identityApplicationDc")
@LoadDataBeforeShow
@ProcessForm(
        outcomes = {
                @Outcome(id = "Accept"),
                @Outcome(id = "rework")
        }
)
public class ClientAppHeadManager extends StandardEditor<IdentityApplication> {

    @Inject
    private TextArea<String> finComment;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private ProcessFormContext processFormContext;
    @ProcessVariable(name = "clientApplication")
    private IdentityApplication application;
    @Inject
    private DataManager dataManager;
    @Inject
    private InstancePropertyContainer<Identity> identityDc;

    @Subscribe
    public void onInit(InitEvent event) {
        if (application != null) {
            application = dataManager
                    .reload(application, "application-view");
            Identity identity = application.getIdentity();
            identity = dataManager.reload(identity, "identity-view");
            identityDc.setItem(identity);
            setEntityToEdit(application);
        }
    }

    @Subscribe("rework")
    public void onReworkClick(Button.ClickEvent event) {
        if (!Strings.isNullOrEmpty(finComment.getRawValue())) {

            processFormContext.taskCompletion()
                    .withOutcome("rework")
                    .saveInjectedProcessVariables()
                    .complete();

            closeWithDiscard();
        } else {
            notifications.create().withCaption(messageBundle.getMessage("writeDownCommentForFront"))
                    .withType(Notifications.NotificationType.WARNING).show();
            finComment.focus();
        }
    }


    @Subscribe("Accept")
    public void onAcceptClick(Button.ClickEvent event) {
        finComment.setValue(null);
        processFormContext.taskCompletion()
                .withOutcome("Accept")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDiscard();
    }

}
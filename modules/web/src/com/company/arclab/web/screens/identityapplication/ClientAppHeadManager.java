package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.DocFormed;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.TManager;
import com.company.arclab.event.UpdateEcpListEvent;
import com.company.arclab.service.BPMNService;
import com.company.arclab.web.screens.kalkan.KalkanCryptSignXmlFragment;
import com.company.arclab.web.screens.kalkan.KalkanSignaturesListTable;
import com.google.common.base.Strings;
import com.haulmont.addon.bproc.web.processform.Outcome;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.global.UserSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.event.EventListener;

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
    @Inject
    private KalkanCryptSignXmlFragment ncaLayerFragment;
    @Inject
    private CollectionPropertyContainer<DocFormed> formedDocsDc;
    @Inject
    private UserSession userSession;

    private String chosenAction = null;
    @Inject
    private KalkanSignaturesListTable signatoryListFragment;
    @Inject
    private FileStorageService fileStorageService;

    @Subscribe
    public void onInit(InitEvent event) {
        if (application != null) {
            application = dataManager
                    .reload(application, "identityApplication-view");
            Identity identity = application.getIdentity();
            identity = dataManager.reload(identity, "identity-view");
            identityDc.setItem(identity);
            setEntityToEdit(application);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (formedDocsDc.getMutableItems().size() > 0 && formedDocsDc.getMutableItems().get(0).getDocFile() != null)
            signatoryListFragment.setEntityId(setFileDescriptor(formedDocsDc.getMutableItems().get(0).getDocFile()));
    }

    public String setFileDescriptor(FileDescriptor fileDescriptor) {
        byte[] bytes = new byte[0];
        try {
            bytes = fileStorageService.loadFile(fileDescriptor);
        } catch (FileStorageException exception) {
            showNotification("Проблема с получением файла " + exception.getLocalizedMessage(), Notifications.NotificationType.WARNING);
        }
        if (bytes.length > 0) {
            return getMd5Hash(bytes);
        }
        return null;
    }

    private String getMd5Hash(byte[] bytes) {
        return DigestUtils
                .md5Hex(bytes).toUpperCase();
    }

    private void showNotification(String message, Notifications.NotificationType notificationType) {
        notifications.create(notificationType)
                .withCaption(message)
                .show();
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
        chosenAction = "approve";
        ncaLayerFragment.setInfoToSign(formedDocsDc.getMutableItems().get(0).getDocFile(),
                identityDc.getItemOrNull(), chosenAction, "Согласовано");
    }

    @EventListener
    public void readMessage(UpdateEcpListEvent event) {
        if (event.getCurrentUser().getLogin() != null) {
            String receiverLogin = event.getCurrentUser().getLogin();
            String currentLogin = userSession.getUser().getLogin();
            if (receiverLogin.equals(currentLogin) && chosenAction != null && chosenAction == "action") {
                finComment.setValue(null);
                processFormContext.taskCompletion()
                        .withOutcome("Accept")
                        .saveInjectedProcessVariables()
                        .complete();
                closeWithDiscard();
            } else {
                notifications.create().withCaption(messageBundle.getMessage("errorWithECP"))
                        .withType(Notifications.NotificationType.ERROR).show();
            }
        }
    }

}
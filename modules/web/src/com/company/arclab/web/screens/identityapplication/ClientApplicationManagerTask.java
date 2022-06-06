package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.DocFormed;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.acts.TWeatherConditions;
import com.company.arclab.service.BPMNService;
import com.company.arclab.service.ClientService;
import com.company.arclab.web.screens.kalkan.KalkanSignaturesListTable;
import com.haulmont.addon.bproc.web.processform.Outcome;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.core.app.EntitySnapshotService;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ContentMode;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.util.OperationResult;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@UiController("arclab_ClientApplicationManagerTask")
@UiDescriptor("client-application-manager-task.xml")
@EditedEntityContainer("identityApplicationDc")
@LoadDataBeforeShow
@ProcessForm(
        outcomes = {
                @Outcome(id = "formDoc"),
                @Outcome(id = "cancel")
        }
)
public class ClientApplicationManagerTask extends StandardEditor<IdentityApplication> {

    //    @ProcessVariable(name = "managerComment")
//    private TextArea<String> managerComment;
    @ProcessVariable(name = "clientApplication")
    private IdentityApplication application;
    @Inject
    private DataManager dataManager;
    @Inject
    private InstancePropertyContainer<Identity> identityDc;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private ClientService clientService;
    @Inject
    private CollectionPropertyContainer<DocFormed> formedDocsDc;
    @Inject
    private EntitySnapshotService entitySnapshotService;
    @Inject
    private ViewRepository viewRepository;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private BPMNService bPMNService;

    @Inject
    private ProcessFormContext processFormContext;
    @Inject
    private TextArea<String> managerComment;
    @Inject
    private KalkanSignaturesListTable signatoryListFragment;
    @Inject
    private GroupBoxLayout signatoryListGroupBox;
    @Inject
    private InstanceContainer<TWeatherConditions> tWeatherConditionsNewDc;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<TWeatherConditions> tWeatherMeasurementsDc;

    @Subscribe
    public void onInit(InitEvent event) {
        if (application != null) {
            application = dataManager
                    .reload(application, "identityApplication-view");
            Identity client = application.getIdentity();

            //setClientRoleIfNotExists(client);
            identityDc.setItem(client);
            setEntityToEdit(application);
            initTable();
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        signatoryListGroupBox.setVisible(signatoryListFragment.getEdsRegistryList().size() != 0);

        if (tWeatherConditionsNewDc.getItemOrNull() == null) {
            tWeatherConditionsNewDc.setItem(metadata.create(TWeatherConditions.class));
        }
    }


    private void initTable() {
//                List<BpmCustomResult> results = getOutcomes();
//                bpmResultTableDc.getMutableItems().clear();
//                if (!results.isEmpty()) {
//                        results.stream().forEach(r -> bpmResultTableDc.getMutableItems().add(r));
//                }
    }

    @Subscribe("saveTemp")
    public void onSaveTempClick(Button.ClickEvent event) {
        if (hasUnsavedChanges()) {
            try {
                Identity client = callTempSave();
                if (client != null) {
                    closeWithDiscard();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                notifications.create().withType(Notifications.NotificationType.ERROR)
                        .withCaption("Не удалось сохранить изменения...")
                        .withDescription(e.getMessage()).show();
                closeWithDiscard();
            }
        } else
            closeWithDiscard();
    }

    private Identity callTempSave() {
        Identity client = identityDc.getItem();
        CommitContext commitContext = new CommitContext();
        commitContext.setCommitInstances(dataContext.getModified());
        commitContext.setRemoveInstances(dataContext.getRemoved());
        client = clientService.saveTemporary(commitContext, client);
        return client;
    }

    @Subscribe("sendToApproval")
    public void onSendToApprovalClick(Button.ClickEvent event) {
        if (formedDocsDc.getMutableItems().isEmpty()) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Требуется прикрепить как минимум 1 документ, во вкладке 'Документы'")
                    .withContentMode(ContentMode.HTML)
                    .show();
            return;
        }

        EntitySnapshot cachedClient = customCommitChanges();
        if (cachedClient != null) {
//            bPMNService.addTaskComment(getEditedEntity().getProcId(), processFormContext.getTaskData(), messageBundle.getMessage("formDoc")
//                    , managerComment.getValue(), application);
            managerComment.setValue(null);
            processFormContext.taskCompletion()
                    .withOutcome("formDoc")
                    .saveInjectedProcessVariables()
                    .addProcessVariable("cachedClient", cachedClient)
                    .complete();
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Заявка отправлена на санкцию")
                    .show();
            closeWithDiscard();
        } else
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка сохранения данных : попробуйте переоткрыть экран")
                    .withContentMode(ContentMode.HTML)
                    .show();
    }

    private EntitySnapshot customCommitChanges() {
        try {
            OperationResult result = commitChanges();
            if (result.getStatus().equals(OperationResult.Status.SUCCESS)) {
                EntitySnapshot cachedClient = null;
                Identity client = getEditedEntity().getIdentity();
                if (client != null) {
                    cachedClient = entitySnapshotService
                            .createSnapshot(client, viewRepository
                                    .findView(client.getMetaClass(), "identity-view"));
                }
                return cachedClient;
            }

        } catch (RemoteException e) {
            if (e.contains("javax.persistence.OptimisticLockException")) {
                IdentityApplication latestClientApp = dataManager.load(Id.of(getEditedEntity())).optional().orElse(null);
                Identity latestClient = dataManager.load(Id.of(getEditedEntity().getIdentity())).optional().orElse(null);
                if (latestClient != null)
                    notifications.create()
                            .withCaption("Сущность ранее была изменена")
                            .withDescription(String.format(
                                    "version: %d, name: %s", latestClient.getVersion(), latestClient.getIinBin()) + " "
                                    + e.getMessage())
                            .show();
            } else {
                log.error(e.getMessage(), e);
                notifications.create().withType(Notifications.NotificationType.ERROR)
                        .withCaption(messageBundle.getMessage("optimisticLock"))
                        .withDescription(e.getMessage()).show();
            }
            Identity client = callTempSave();
            if (client != null)
                identityDc.setItem(client);
            else closeWithDiscard();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Subscribe("createButton")
    public void onCreateButtonClick(Button.ClickEvent event) {
        TWeatherConditions tWeatherConditions = tWeatherConditionsNewDc.getItem();
        tWeatherConditions.setIdentity(getEditedEntity().getIdentity());
        tWeatherConditions.setTakenDate(convertToLocalDateTimeViaInstant(new Date()));
        tWeatherMeasurementsDc.getMutableItems().add(tWeatherConditions);
        tWeatherConditionsNewDc.setItem(metadata.create(TWeatherConditions.class));
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
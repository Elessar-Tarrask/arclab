package com.company.arclab.web.screens.application;

import com.company.arclab.entity.application.EClientApplicationType;
import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.dict.EClientType;
import com.company.arclab.service.ClientCreateService;
import com.company.arclab.web.screens.identityapplication.IdentityApplicationEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.Map;

@UiController("arclab_CreateClient")
@UiDescriptor("create-client.xml")
public class CreateClient extends Screen {
    @Inject
    private TextField<String> iinField;
    @Inject
    private TextField<String> clientFullnameField;
    private final String PROCESS = "client-create";
    @Inject
    private ClientCreateService clientCreateService;
    @Inject
    private Notifications notifications;
    @Inject
    private DataManager dataManager;
    @Inject
    private ButtonsPanel createButtonPanel;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UserSessionSource userSessionSource;

    @Subscribe("createButton")
    public void onCreateButtonClick(Button.ClickEvent event) {
        createButtonPanel.setVisible(false);
        Map<IdentityApplication, String> clientAppMap = clientCreateService.startClientApplication(iinField.getRawValue(),
                clientFullnameField.getRawValue(),
                EClientType.INDIVIDUAL.getId(),
                EClientApplicationType.CREATE.getId(),
                userSessionSource.getUserSession().getUser(),
                PROCESS);
        if (clientAppMap == null) {
            return;
        }
        IdentityApplication identityApplication = clientAppMap.keySet().stream().findFirst().orElse(null);
        if (identityApplication != null) {
            // существующая заявка
//            if (clientAppMap.get(identityApplication).equals("clientAppExist")) {
//                notifications.create().withType(Notifications.NotificationType.HUMANIZED)
//                        .withCaption("Создан новый клиент").show();
//            } else {
                notifications.create().withType(Notifications.NotificationType.HUMANIZED)
                        .withCaption("Создан новый клиент").show();
//            }
            closeWithDefaultAction();
            goToClientAppScreen(identityApplication);
        } else {
            notifications.create().withType(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка").show();
            closeWithDefaultAction();
        }
    }

    private void goToClientAppScreen(IdentityApplication identityApplication) {
        identityApplication = dataManager.reload(identityApplication, "identityApplication-view");
        IdentityApplicationEdit edt = screenBuilders.editor(IdentityApplication.class, this)
                .withScreenClass(IdentityApplicationEdit.class)
                .editEntity(identityApplication)
                .build();
        edt.setReadOnly(true);
        edt.show();
    }
}
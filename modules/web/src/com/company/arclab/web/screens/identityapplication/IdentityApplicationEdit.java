package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.EClientApplicationType;
import com.company.arclab.entity.client.DocFormed;
import com.company.arclab.web.screens.BpmPanelCustomFragment;
import com.company.arclab.web.screens.kalkan.KalkanSignaturesListTable;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.IdentityApplication;
import org.slf4j.Logger;

import javax.inject.Inject;

@UiController("arclab_IdentityApplication.edit")
@UiDescriptor("identity-application-edit.xml")
@EditedEntityContainer("identityApplicationDc")
@LoadDataBeforeShow
public class IdentityApplicationEdit extends StandardEditor<IdentityApplication> {
    private static final String PROCESS_DEF_KEY = "client-create";
    private static final String PROCESS_DEF_KEY_EDIT = "client-edit";
    @Inject
    private Logger log;
    @Inject
    private DataManager dataManager;
    @Inject
    private InstanceContainer<IdentityApplication> identityApplicationDc;
    @Inject
    private BpmPanelCustomFragment bpmPanelFragment;
    @Inject
    private KalkanSignaturesListTable signatoryListFragment;
    @Inject
    private GroupBoxLayout signatoryListGroupBox;
    @Inject
    private CollectionPropertyContainer<DocFormed> formedDocsDc;

    private void setBpmAction() {
        if (EClientApplicationType.CREATE == getEditedEntity().getApplicationType())
            bpmPanelFragment.setBpmPanelParams(getEditedEntity(), PROCESS_DEF_KEY/*, getProcData(PROCESS_DEF_KEY)*/);
        else
            bpmPanelFragment.setBpmPanelParams(getEditedEntity(), PROCESS_DEF_KEY_EDIT/*, getProcData(PROCESS_DEF_KEY_EDIT)*/);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        //signatoryListGroupBox.setVisible(formedDocsDc.getMutableItems().isEmpty());

        setBpmAction();
    }


    private void preventErrorIfCameFromParentAppScreen() {
        IdentityApplication application = getEditedEntity();
        try {
            if (application.getApplicationType() == null)
                log.info("appType null but it's in context..");
        } catch (RuntimeException e) {
            log.error("Caught an error 'cos comming from parent app screen -\n" + e.getMessage()
                    + "\n to solve this issue - reloading the client App context..");
            application = dataManager.reload(application, "identityApplication-view");
            identityApplicationDc.setItem(application);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateECPListTable();
    }

    public void updateECPListTable() {
        signatoryListFragment.setEntityId(getEditedEntity().getIdentity());
    }
}
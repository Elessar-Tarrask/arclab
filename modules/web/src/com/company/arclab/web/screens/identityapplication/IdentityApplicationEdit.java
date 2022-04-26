package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.EClientApplicationType;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.IdentityApplication;

@UiController("arclab_IdentityApplication.edit")
@UiDescriptor("identity-application-edit.xml")
@EditedEntityContainer("identityApplicationDc")
@LoadDataBeforeShow
public class IdentityApplicationEdit extends StandardEditor<IdentityApplication> {

//    private void setBpmAction() {
//        if (EClientApplicationType.CREATE == getEditedEntity().getApplicationType())
//            bpmPanelFragment.setBpmPanelParams(getEditedEntity(), PROCESS_DEF_KEY/*, getProcData(PROCESS_DEF_KEY)*/);
//        else
//            bpmPanelFragment.setBpmPanelParams(getEditedEntity(), PROCESS_DEF_KEY_EDIT/*, getProcData(PROCESS_DEF_KEY_EDIT)*/);
//    }
}
package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.application.IdentityApplication;
import com.haulmont.addon.bproc.web.processform.Outcome;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

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
}
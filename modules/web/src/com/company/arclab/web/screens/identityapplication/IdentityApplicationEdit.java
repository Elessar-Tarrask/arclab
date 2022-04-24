package com.company.arclab.web.screens.identityapplication;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.IdentityApplication;

@UiController("arclab_IdentityApplication.edit")
@UiDescriptor("identity-application-edit.xml")
@EditedEntityContainer("identityApplicationDc")
@LoadDataBeforeShow
public class IdentityApplicationEdit extends StandardEditor<IdentityApplication> {
}
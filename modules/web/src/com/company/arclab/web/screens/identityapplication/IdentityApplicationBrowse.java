package com.company.arclab.web.screens.identityapplication;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.IdentityApplication;

@UiController("arclab_IdentityApplication.browse")
@UiDescriptor("identity-application-browse.xml")
@LookupComponent("identityApplicationsTable")
@LoadDataBeforeShow
public class IdentityApplicationBrowse extends StandardLookup<IdentityApplication> {
}
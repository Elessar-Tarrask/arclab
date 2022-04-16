package com.company.arclab.web.screens.application;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.Application;

@UiController("jcrm_Application.browse")
@UiDescriptor("application-browse.xml")
@LookupComponent("applicationsTable")
@LoadDataBeforeShow
public class ApplicationBrowse extends StandardLookup<Application> {
}
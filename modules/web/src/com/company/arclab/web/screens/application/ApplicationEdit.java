package com.company.arclab.web.screens.application;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.Application;

@UiController("jcrm_Application.edit")
@UiDescriptor("application-edit.xml")
@EditedEntityContainer("applicationDc")
@LoadDataBeforeShow
public class ApplicationEdit extends StandardEditor<Application> {
}
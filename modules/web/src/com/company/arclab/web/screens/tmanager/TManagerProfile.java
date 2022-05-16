package com.company.arclab.web.screens.tmanager;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TManager;

@UiController("arclab_TManager.profile")
@UiDescriptor("t-manager-profile.xml")
@EditedEntityContainer("tManagerDc")
//@LoadDataBeforeShow
public class TManagerProfile extends StandardEditor<TManager> {
}
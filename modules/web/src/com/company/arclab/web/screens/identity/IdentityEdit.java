package com.company.arclab.web.screens.identity;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.Identity;

@UiController("arclab_Identity.edit")
@UiDescriptor("identity-edit.xml")
@EditedEntityContainer("identityDc")
@LoadDataBeforeShow
public class IdentityEdit extends StandardEditor<Identity> {
}
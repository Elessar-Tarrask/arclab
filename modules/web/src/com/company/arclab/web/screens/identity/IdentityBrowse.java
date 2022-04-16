package com.company.arclab.web.screens.identity;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.Identity;

@UiController("arclab_Identity.browse")
@UiDescriptor("identity-browse.xml")
@LookupComponent("identitiesTable")
@LoadDataBeforeShow
public class IdentityBrowse extends StandardLookup<Identity> {
}
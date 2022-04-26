package com.company.arclab.web.screens.edsregistry;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kalkan.EdsRegistry;

@UiController("arclab_EdsRegistry.browse")
@UiDescriptor("eds-registry-browse.xml")
@LookupComponent("edsRegistriesTable")
@LoadDataBeforeShow
public class EdsRegistryBrowse extends StandardLookup<EdsRegistry> {
}
package com.company.arclab.web.screens.dsampletypes;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.DSampleTypes;

@UiController("arclab_DSampleTypes.browse")
@UiDescriptor("d-sample-types-browse.xml")
@LookupComponent("dSampleTypesesTable")
@LoadDataBeforeShow
public class DSampleTypesBrowse extends StandardLookup<DSampleTypes> {
}
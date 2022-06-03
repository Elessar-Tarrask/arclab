package com.company.arclab.web.screens.dsampletypes;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.DSampleTypes;

@UiController("arclab_DSampleTypes.edit")
@UiDescriptor("d-sample-types-edit.xml")
@EditedEntityContainer("dSampleTypesDc")
@LoadDataBeforeShow
public class DSampleTypesEdit extends StandardEditor<DSampleTypes> {
}
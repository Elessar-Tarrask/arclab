package com.company.arclab.web.screens.dcontactkind;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.DContactKind;

@UiController("jcrm_DContactKind.edit")
@UiDescriptor("d-contact-kind-edit.xml")
@EditedEntityContainer("dContactKindDc")
@LoadDataBeforeShow
public class DContactKindEdit extends StandardEditor<DContactKind> {
}
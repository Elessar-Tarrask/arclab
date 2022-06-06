package com.company.arclab.web.screens.dcontactstype;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.DContactsType;

@UiController("jcrm_DContactsType.edit")
@UiDescriptor("d-contacts-type-edit.xml")
@EditedEntityContainer("dContactsTypeDc")
@LoadDataBeforeShow
public class DContactsTypeEdit extends StandardEditor<DContactsType> {
}
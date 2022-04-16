package com.company.arclab.web.screens.daddresstype;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.DAddressType;

@UiController("jcrm_DAddressType.edit")
@UiDescriptor("d-address-type-edit.xml")
@EditedEntityContainer("dAddressTypeDc")
@LoadDataBeforeShow
public class DAddressTypeEdit extends StandardEditor<DAddressType> {
}
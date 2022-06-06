package com.company.arclab.web.screens.tclientaddress;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TClientAddress;

@UiController("jcrm_TClientAdress.edit")
@UiDescriptor("t-client-address-edit.xml")
@EditedEntityContainer("tClientAddressDc")
@LoadDataBeforeShow
public class TClientAddressEdit extends StandardEditor<TClientAddress> {
}
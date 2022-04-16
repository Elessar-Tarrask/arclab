package com.company.arclab.web.screens.daddresstype;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.DAddressType;

@UiController("jcrm_DAddressType.browse")
@UiDescriptor("d-address-type-browse.xml")
@LookupComponent("dAddressTypesTable")
@LoadDataBeforeShow
public class DAddressTypeBrowse extends StandardLookup<DAddressType> {
}
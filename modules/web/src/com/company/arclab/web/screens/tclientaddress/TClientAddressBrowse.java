package com.company.arclab.web.screens.tclientaddress;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TClientAddress;

@UiController("jcrm_TClientAdress.browse")
@UiDescriptor("t-client-address-browse.xml")
@LookupComponent("tClientAddressesTable")
@LoadDataBeforeShow
public class TClientAddressBrowse extends StandardLookup<TClientAddress> {
}
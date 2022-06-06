package com.company.arclab.web.screens.tcontactphone;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TContactPhone;

@UiController("jcrm_TContactPhone.browse")
@UiDescriptor("t-contact-phone-browse.xml")
@LookupComponent("tContactPhonesTable")
@LoadDataBeforeShow
public class TContactPhoneBrowse extends StandardLookup<TContactPhone> {
}
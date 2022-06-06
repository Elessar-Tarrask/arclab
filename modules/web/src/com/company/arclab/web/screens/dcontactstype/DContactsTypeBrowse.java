package com.company.arclab.web.screens.dcontactstype;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.DContactsType;

@UiController("jcrm_DContactsType.browse")
@UiDescriptor("d-contacts-type-browse.xml")
@LookupComponent("dContactsTypesTable")
@LoadDataBeforeShow
public class DContactsTypeBrowse extends StandardLookup<DContactsType> {
}
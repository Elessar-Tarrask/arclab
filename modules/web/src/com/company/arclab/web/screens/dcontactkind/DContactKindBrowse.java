package com.company.arclab.web.screens.dcontactkind;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.DContactKind;

@UiController("jcrm_DContactKind.browse")
@UiDescriptor("d-contact-kind-browse.xml")
@LookupComponent("dContactKindsTable")
@LoadDataBeforeShow
public class DContactKindBrowse extends StandardLookup<DContactKind> {
}
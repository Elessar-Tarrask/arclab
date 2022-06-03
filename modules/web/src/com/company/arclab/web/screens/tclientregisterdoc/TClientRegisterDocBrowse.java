package com.company.arclab.web.screens.tclientregisterdoc;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TClientRegisterDoc;

@UiController("jcrm_TClientRegisterDoc.browse")
@UiDescriptor("t-client-register-doc-browse.xml")
@LookupComponent("tClientRegisterDocsTable")
@LoadDataBeforeShow
public class TClientRegisterDocBrowse extends StandardLookup<TClientRegisterDoc> {
}
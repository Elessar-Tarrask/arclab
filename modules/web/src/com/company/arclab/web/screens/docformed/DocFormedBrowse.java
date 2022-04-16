package com.company.arclab.web.screens.docformed;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.DocFormed;

@UiController("jcrm_DocFormed.browse")
@UiDescriptor("doc-formed-browse.xml")
@LookupComponent("docFormedsTable")
@LoadDataBeforeShow
public class DocFormedBrowse extends StandardLookup<DocFormed> {
}
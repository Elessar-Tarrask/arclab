package com.company.arclab.web.screens.tidentdoc;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TIdentDoc;

@UiController("jcrm_TIdentDoc.browse")
@UiDescriptor("t-ident-doc-browse.xml")
@LookupComponent("tIdentDocsTable")
@LoadDataBeforeShow
public class TIdentDocBrowse extends StandardLookup<TIdentDoc> {
}
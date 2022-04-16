package com.company.arclab.web.screens.docformed;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.DocFormed;

@UiController("jcrm_DocFormed.edit")
@UiDescriptor("doc-formed-edit.xml")
@EditedEntityContainer("docFormedDc")
@LoadDataBeforeShow
public class DocFormedEdit extends StandardEditor<DocFormed> {
}
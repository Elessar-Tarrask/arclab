package com.company.arclab.web.screens.docformed;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.DocFormed;

import javax.inject.Inject;

@UiController("jcrm_DocFormed.edit")
@UiDescriptor("doc-formed-edit.xml")
@EditedEntityContainer("docFormedDc")
@LoadDataBeforeShow
public class DocFormedEdit extends StandardEditor<DocFormed> {
    @Inject
    private TextField<String> docTypeField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        docTypeField.setValue("Базовые Документы");
    }


}
package com.company.arclab.web.screens.tidentdoc;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TIdentDoc;

@UiController("jcrm_TIdentDoc.edit")
@UiDescriptor("t-ident-doc-edit.xml")
@EditedEntityContainer("tIdentDocDc")
@LoadDataBeforeShow
public class TIdentDocEdit extends StandardEditor<TIdentDoc> {
}
package com.company.arclab.web.screens.tclientregisterdoc;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TClientRegisterDoc;

@UiController("jcrm_TClientRegisterDoc.edit")
@UiDescriptor("t-client-register-doc-edit.xml")
@EditedEntityContainer("tClientRegisterDocDc")
@LoadDataBeforeShow
public class TClientRegisterDocEdit extends StandardEditor<TClientRegisterDoc> {
}
package com.company.arclab.web.screens.dkato;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.DKato;

@UiController("jcrm_DKato.edit")
@UiDescriptor("d-kato-edit.xml")
@EditedEntityContainer("dKatoDc")
@LoadDataBeforeShow
public class DKatoEdit extends StandardEditor<DKato> {
}
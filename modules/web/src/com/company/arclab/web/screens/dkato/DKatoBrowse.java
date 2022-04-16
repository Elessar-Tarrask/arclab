package com.company.arclab.web.screens.dkato;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.DKato;

@UiController("jcrm_DKato.browse")
@UiDescriptor("d-kato-browse.xml")
@LookupComponent("dKatoesTable")
@LoadDataBeforeShow
public class DKatoBrowse extends StandardLookup<DKato> {
}
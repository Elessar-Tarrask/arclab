package com.company.arclab.web.screens.dgasanalyzer;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.DGasAnalyzer;

@UiController("arclab_DGasAnalyzer.browse")
@UiDescriptor("d-gas-analyzer-browse.xml")
@LookupComponent("dGasAnalyzersTable")
@LoadDataBeforeShow
public class DGasAnalyzerBrowse extends StandardLookup<DGasAnalyzer> {
}
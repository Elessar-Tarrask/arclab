package com.company.arclab.web.screens.dgasanalyzer;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.DGasAnalyzer;

@UiController("arclab_DGasAnalyzer.edit")
@UiDescriptor("d-gas-analyzer-edit.xml")
@EditedEntityContainer("dGasAnalyzerDc")
@LoadDataBeforeShow
public class DGasAnalyzerEdit extends StandardEditor<DGasAnalyzer> {
}
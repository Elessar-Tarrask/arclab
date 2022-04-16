package com.company.arclab.web.screens.measuringinstruments;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.MeasuringInstruments;

@UiController("arclab_MeasuringInstruments.edit")
@UiDescriptor("measuring-instruments-edit.xml")
@EditedEntityContainer("measuringInstrumentsDc")
@LoadDataBeforeShow
public class MeasuringInstrumentsEdit extends StandardEditor<MeasuringInstruments> {
}
package com.company.arclab.web.screens.measuringinstruments;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.dict.MeasuringInstruments;

@UiController("arclab_MeasuringInstruments.browse")
@UiDescriptor("measuring-instruments-browse.xml")
@LookupComponent("measuringInstrumentsesTable")
@LoadDataBeforeShow
public class MeasuringInstrumentsBrowse extends StandardLookup<MeasuringInstruments> {
}
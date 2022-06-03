package com.company.arclab.web.screens.tweatherconditions;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.TWeatherConditions;

@UiController("arclab_TWeatherConditions.browse")
@UiDescriptor("t-weather-conditions-browse.xml")
@LookupComponent("tWeatherConditionsesTable")
@LoadDataBeforeShow
public class TWeatherConditionsBrowse extends StandardLookup<TWeatherConditions> {
}
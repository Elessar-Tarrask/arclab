package com.company.arclab.web.screens.tweatherconditions;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.TWeatherConditions;

@UiController("arclab_TWeatherConditions.edit")
@UiDescriptor("t-weather-conditions-edit.xml")
@EditedEntityContainer("tWeatherConditionsDc")
@LoadDataBeforeShow
public class TWeatherConditionsEdit extends StandardEditor<TWeatherConditions> {
}
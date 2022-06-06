package com.company.arclab.web.screens.tweatherconditions;

import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.acts.TWeatherConditions;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UiController("arclab_TWeatherConditions.edit")
@UiDescriptor("t-weather-conditions-edit.xml")
@EditedEntityContainer("tWeatherConditionsDc")
@LoadDataBeforeShow
public class TWeatherConditionsEdit extends StandardEditor<TWeatherConditions> {
    @Inject
    private DateField<LocalDateTime> takenDateField;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        takenDateField.setValue(convertToLocalDateTimeViaInstant(new Date()));
    }


    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
package com.company.arclab.web.screens.city;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.City;

@UiController("jcrm_City.edit")
@UiDescriptor("city-edit.xml")
@EditedEntityContainer("cityDc")
@LoadDataBeforeShow
public class CityEdit extends StandardEditor<City> {
}
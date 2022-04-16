package com.company.arclab.web.screens.city;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.City;

@UiController("jcrm_City.browse")
@UiDescriptor("city-browse.xml")
@LookupComponent("citiesTable")
@LoadDataBeforeShow
public class CityBrowse extends StandardLookup<City> {
}
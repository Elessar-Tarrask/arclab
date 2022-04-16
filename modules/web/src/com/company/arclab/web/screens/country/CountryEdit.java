package com.company.arclab.web.screens.country;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.Country;

@UiController("jcrm_Country.edit")
@UiDescriptor("country-edit.xml")
@EditedEntityContainer("countryDc")
@LoadDataBeforeShow
public class CountryEdit extends StandardEditor<Country> {
}
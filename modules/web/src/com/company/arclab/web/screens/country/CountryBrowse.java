package com.company.arclab.web.screens.country;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kato.Country;

@UiController("jcrm_Country.browse")
@UiDescriptor("country-browse.xml")
@LookupComponent("countriesTable")
@LoadDataBeforeShow
public class CountryBrowse extends StandardLookup<Country> {
}
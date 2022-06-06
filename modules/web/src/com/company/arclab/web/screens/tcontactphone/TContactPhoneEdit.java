package com.company.arclab.web.screens.tcontactphone;

import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TContactPhone;

@UiController("jcrm_TContactPhone.edit")
@UiDescriptor("t-contact-phone-edit.xml")
@EditedEntityContainer("tContactPhoneDc")
@LoadDataBeforeShow
public class TContactPhoneEdit extends StandardEditor<TContactPhone> {
}
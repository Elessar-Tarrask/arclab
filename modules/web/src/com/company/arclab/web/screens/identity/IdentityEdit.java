package com.company.arclab.web.screens.identity;

import com.company.arclab.web.screens.kalkan.KalkanSignaturesListTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.Identity;

import javax.inject.Inject;

@UiController("arclab_Identity.edit")
@UiDescriptor("identity-edit.xml")
@EditedEntityContainer("identityDc")
@LoadDataBeforeShow
public class IdentityEdit extends StandardEditor<Identity> {

    @Inject
    private KalkanSignaturesListTable signatoryListFragment;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        signatoryListFragment.setEntityId(getEditedEntity());
    }
}
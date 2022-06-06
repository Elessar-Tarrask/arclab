package com.company.arclab.web.screens.tmanager;

import com.company.arclab.entity.client.TClientDigitalSign;
import com.company.arclab.service.OfficerService;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.client.TManager;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.entity.UserSubstitution;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.stream.Collectors;

@UiController("arclab_TManager.profile")
@UiDescriptor("t-manager-profile.xml")
@EditedEntityContainer("tManagerDc")
//@LoadDataBeforeShow
public class TManagerProfile extends StandardEditor<TManager> {
    @Inject
    private OfficerService officerService;
    @Inject
    private UserSession userSession;
    @Inject
    private CollectionContainer<UserSubstitution> substitutionsDc;
    @Inject
    private CollectionContainer<UserRole> userRolesDc;
    @Inject
    private InstanceContainer<TManager> tManagerDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        TManager officer = officerService.getOfficerByUser(userSession.getUser());
        substitutionsDc.setItems(officer.getSubstitutions());
        userRolesDc.setItems(officer.getUserRoles().stream().filter(userRole -> userRole.getRole() != null).collect(Collectors.toList()));
        tManagerDc.setItem(officer);

//        TClientDigitalSign tClientDigitalSign = digitalSignDc.getItemOrNull();
//        if (tClientDigitalSign != null) {
//            String fullName = tClientDigitalSign.getSignatoryCN() + " " + tClientDigitalSign.getSignatoryG();
//            signatoryFullName.setValue(fullName);
//            signatoryValidityPeriodFrom.setValue(tClientDigitalSign.getSignatoryDateFrom());
//            signatoryValidityPeriodTill.setValue(tClientDigitalSign.getSignatoryDateTill());
//
//            signatoryOrganization.setValue("First Heartland Jysan Bank");
//            signatorySupervisor.setValue("-");
//        } else
//            digitalSignForm.setVisible(false);
        setReadOnly(true);
    }
}
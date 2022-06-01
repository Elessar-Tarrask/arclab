package com.company.arclab.web.screens.kalkan;

import com.company.arclab.entity.client.TClientDigitalSign;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.StandardOutcome;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;

@UiController("arclab_DigitalSignatoryInfo")
@UiDescriptor("digital-signatory-info.xml")
public class DigitalSignatoryInfo extends Screen {
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;
    @Inject
    private InstanceContainer<TClientDigitalSign> tClientDigitalSignDc;
    @Inject
    private TextField<String> signatoryFullName;
    @Inject
    private TextField<String> signatoryValidityPeriodFrom;
    @Inject
    private TextField<String> signatoryValidityPeriodTill;
    @Inject
    private TextField<String> signatorySupervisor;
    @Inject
    private TextField<String> signatoryOrganization;
    @Inject
    private Events events;

    public void setTClientDigitalSignDc(TClientDigitalSign tClientDigitalSign){
        tClientDigitalSignDc.setItem(tClientDigitalSign);
        String fullName = tClientDigitalSign.getSignatorySurname() + " " + tClientDigitalSign.getSignatoryG();
        signatoryFullName.setValue(fullName);
//        tClientDigitalSignDc.getItem().setSignatoryAction(null);
//        signatoryValidityPeriodFrom.setValue("2021-05-08");
//        signatoryValidityPeriodTill.setValue("2022-05-08");
//
        signatoryOrganization.setValue("ARCLAB");
        signatorySupervisor.setValue("-");
    }

    @Subscribe
    public void onInit(InitEvent event) {
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        TClientDigitalSign tClientDigitalSign = dataManager.create(TClientDigitalSign.class);
    }

    @Subscribe("chooseAnotherCertificate")
    public void onChooseAnotherCertificateClick(Button.ClickEvent event) {
        tClientDigitalSignDc.getItem().setSignatoryAction("chooseAnotherCertificate");
        dataManager.commit(tClientDigitalSignDc.getItem());
        close(StandardOutcome.SELECT);
    }

    @Subscribe("signActionCMS")
    public void onSignActionCMSClick(Button.ClickEvent event) {
        tClientDigitalSignDc.getItem().setSignatoryAction("signActionCMS");
        dataManager.commit(tClientDigitalSignDc.getItem());
        close(StandardOutcome.SELECT);
    }

    @Subscribe
    public void onBeforeClose(BeforeCloseEvent event) {
        if (tClientDigitalSignDc.getItem().getSignatoryAction() == null)
            dataManager.commit(tClientDigitalSignDc.getItem());
    }

}
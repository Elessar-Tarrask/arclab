package com.company.arclab.web.screens.kalkan;

import com.company.arclab.entity.kalkan.EdsRegistry;
import com.company.arclab.event.UpdateEcpListEvent;
import com.company.arclab.service.EdsService;
import com.company.arclab.service.KalkanSignVerifyService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Formatter;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.global.UserSession;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;

@UiController("arclab_KalkanSignaturesListTable")
@UiDescriptor("kalkan-signatures-list-table.xml")
public class KalkanSignaturesListTable extends ScreenFragment {

    @Inject
    private CollectionContainer<EdsRegistry> edsRegistryDc;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<EdsRegistry> edsRegistryDl;
    @Inject
    private UserSession userSession;
    @Inject
    private KalkanSignVerifyService kalkanSignVerifyService;
    @Inject
    private Notifications notifications;
    @Inject
    private Table<EdsRegistry> edsRegistryTable;

    private Entity currentEntityToUpdate;
    @Inject
    private EdsService edsService;

    public List<EdsRegistry> getEdsRegistryList() {
        return edsRegistryDc.getMutableItems();
    }

    @Install(to = "edsRegistryTable", subject = "styleProvider")
    protected String customerTableStyleProvider(EdsRegistry edsRegistry, String property) {
        //if (property != null && property.equals("signResult")) {
        if (edsRegistry.getSignResult()) {
            return "error-sign";
        } else if (!(edsRegistry.getSignResult())) {
            return "good-sign";
        }
        //}
        return null;
    }

    public static class UppercaseFormatter implements Formatter<Boolean> {
        @Override
        public String format(Boolean value) {
            return value ? "Валидно" : "Не валидно";
        }
    }

    public void setEntityId(Entity entity) {
        currentEntityToUpdate = entity;
        edsRegistryDc.setItems(edsService.loadFormDb(entity));
        edsRegistryDl.load();
    }

    @Subscribe
    public void onInit(ScreenFragment.InitEvent event) {
        Function UppercaseFormatter = new UppercaseFormatter();
        edsRegistryTable.getColumn("signResult").setFormatter(UppercaseFormatter);
    }

    @Install(to = "edsRegistryDl", target = Target.DATA_LOADER)
    private List<EdsRegistry> edsRegistryDlLoadDelegate(LoadContext<EdsRegistry> loadContext) {
        return edsRegistryDc.getItems();
    }


    @EventListener
    public void readMessage(UpdateEcpListEvent event) {
        if (event.getCurrentUser().getLogin() != null) {
            String receiverLogin = event.getCurrentUser().getLogin();
            String currentLogin = userSession.getUser().getLogin();
            if (receiverLogin.equals(currentLogin))
                edsRegistryDc.setItems(edsService.loadFormDb(event.getEntity()));
            edsRegistryDl.load();
        }
    }

    private void showNotification(String message) {
        notifications.create().withCaption(message).show();
    }
}
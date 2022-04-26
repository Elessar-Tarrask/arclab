package com.company.arclab.web.screens.identity;

import com.company.arclab.entity.application.EClientApplicationType;
import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.dict.EClientStatus;
import com.company.arclab.service.ClientCreateService;
import com.company.arclab.service.ClientSearchService;
import com.company.arclab.web.screens.application.CreateClient;
import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.QueryUtils;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.SuggestionField;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UiController("arclab_IdentitySerachScreen")
@UiDescriptor("identity-serach-screen.xml")
public class IdentitySerachScreen extends Screen {

    @Inject
    private SuggestionField searchByName;
    @Inject
    private SuggestionField searchIinBin;
    @Inject
    private Screens screens;
    @Inject
    private CollectionLoader<Identity> resultDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private ClientSearchService clientSearchService;
    @Inject
    private CollectionContainer<Identity> searchtClientDc;
    @Inject
    private GroupTable<Identity> searchResultTable;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Button addClientButtonSearchForm;

    @Subscribe
    public void onInit(InitEvent event) {
        resultDl.load();

        searchByName.setSuggestionsLimit(100);
        searchByName.setMinSearchStringLength(3);
        searchByName.setAsyncSearchDelayMs(500);

        initSearchName();

        initSearchIinBin();
    }

    private void initSearchIinBin() {
        searchByName.setSuggestionsLimit(100);
        searchByName.setMinSearchStringLength(3);
        searchByName.setAsyncSearchDelayMs(500);

        searchIinBin.setSearchExecutor(((searchString, searchParams) -> {
            final String escapedIinBin = QueryUtils.escapeForLike(searchString).trim();

            List<Identity> listClient = dataManager.loadList(LoadContext.create(Identity.class).setQuery(
                    LoadContext
                            .createQuery("select c from arclab_Identity c where c.iinBin like :iin " +
                                    " and c.status <> 'New' and c.status <> 'Modify'" +
                                    "order by c.iinBin")
                            .setParameter("iin", /*"%" + */escapedIinBin + "%")));

            return listClient
                    .stream()
                    .filter(c -> c.getFullName() != null
                            && c.getIinBin() != null
                            && c.getStatus() != null && !c.getStatus().equals(EClientStatus.NEW))
                    .map(c -> c.getIinBin().concat("\t").concat(c.getFullName()))
                    .collect(Collectors.toList());
        }));
    }

    private void initSearchName() {
        searchByName.setSearchExecutor(((searchString, searchParams) -> {

            searchString = QueryUtils.escapeForLike(searchString);

            List<Identity> listClient = dataManager.loadList(LoadContext.create(Identity.class).setQuery(
                    LoadContext
                            .createQuery("select c from arclab_Identity c where upper(c.fullName) like upper(:name) " +
                                    " and c.status <> 'New' and c.status <> 'Modify' " +
                                    "order by c.fullName")
                            .setParameter("name", "%" + searchString.trim() + "%")));

            return listClient
                    .stream()
                    .filter(c -> c.getFullName() != null
                            && c.getIinBin() != null
                            && c.getStatus() != null && !c.getStatus().equals(EClientStatus.NEW))
                    .map(c -> c.getFullName().concat("\t").concat(c.getIinBin()))
                    .collect(Collectors.toList());
        }));
    }

    @Subscribe("searchByName")
    public void onSearchByNameValueChange(HasValue.ValueChangeEvent event) {
        String client = (String) event.getValue();
        if (client != null) {
            String[] clData = client.split("\t");
            if (clData.length == 2) {
                String name = clData[0];
                String iin = clData[1];
                searchByName.setValue(name);
                searchIinBin.setValue(iin);
            }
        }
    }

    @Subscribe("searchIinBin")
    public void onSearchIinBinValueChange(HasValue.ValueChangeEvent event) {
        String client = (String) event.getValue();
        if (client != null) {
            String[] clData = client.split("\t");
            if (clData.length == 2) {
                String name = clData[1];
                String iin = clData[0];
                searchByName.setValue(name);
                searchIinBin.setValue(iin);
            }
        }
    }

    public void addClientButtonClick() {
        CreateClient createClient = screens.create(CreateClient.class, OpenMode.DIALOG);
        createClient.addAfterCloseListener(l -> {
            resultDl.load();
        });
        screens.show(createClient);
    }

    @Subscribe("searchButton")
    public void onSearchButtonClick(Button.ClickEvent event) {
        searchBtnClick();
        searchResultTable.focus();
    }

    @Install(to = "searchByName", subject = "enterActionHandler")
    private void searchByNameEnterActionHandler(String searchString) {
        enterPush();
    }

    @Install(to = "searchIinBin", subject = "enterActionHandler")
    private void searchIinBinEnterActionHandler(String searchString) {
        enterPush();
    }

    @Subscribe("clrButton")
    public void onClrButtonClick(Button.ClickEvent event) {
        searchtClientDc.setItems(null);
        searchByName.setValue(null);
        searchIinBin.setValue(null);
        searchResultTable.setVisible(false);
    }

    private void enterPush() {
        searchBtnClick();
        Identity identity = searchResultTable.getItems().getItems().stream().findFirst().orElse(null);
        searchResultTable.focus();
        if (identity != null)
            searchResultTable.setSelected(identity);
    }

    private void searchBtnClick() {
        String iin = (String) searchIinBin.getValue();
        String name = (String) searchByName.getValue();

        List<Identity> clientList = null;
        if (!Strings.isNullOrEmpty(iin) && !iin.contains("0000")) {
            clientList = clientSearchService.findAllActiveClientsByIinBin(iin);
        } else if (!Strings.isNullOrEmpty(name))
            clientList = clientSearchService.findAllActiveClientsByName(name);

        if (clientList != null) {
            searchtClientDc.setItems(clientList);
            searchResultTable.setVisible(true);
        } else {
            notifications.create().withCaption(messageBundle.getMessage("clientNotFound")).show();
            addClientButtonSearchForm.setVisible(true);
        }
    }
}
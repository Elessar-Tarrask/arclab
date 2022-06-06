package com.company.arclab.web.screens.identityapplication;

import com.company.arclab.entity.client.TManager;
import com.haulmont.addon.bproc.query.TaskDataQuery;
import com.haulmont.addon.bproc.service.BprocTaskService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.application.IdentityApplication;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.List;

@UiController("arclab_IdentityApplication.browse")
@UiDescriptor("identity-application-browse.xml")
@LookupComponent("identityApplicationsTable")
@LoadDataBeforeShow
public class IdentityApplicationBrowse extends StandardLookup<IdentityApplication> {
    @Inject
    private CollectionContainer<IdentityApplication> identityApplicationsDc;
    @Inject
    private DataManager dataManager;

//    @Install(to = "identityApplicationsDl", target = Target.DATA_LOADER)
//    private List<IdentityApplication> identityApplicationsDlLoadDelegate(LoadContext<IdentityApplication> loadContext) {
//        TManager tManager = dataManager.load(TManager.class)
//                .query("select m from arclab_TManager m where m.login = :login")
//                .parameter("login", )
//                .one();
//        return dataManager.load(IdentityApplication.class)
//                .query("select a from arclab_IdentityApplication a where a.manager = :manager")
//                .parameter("manager", tManager)
//                .view("identityApplication-view")
//                .list();
//    }
    
    
}
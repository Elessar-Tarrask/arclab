package com.company.arclab.service;

import com.company.arclab.entity.client.Identity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(ClientService.NAME)
public class ClientServiceBean implements ClientService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Logger log;

    @Override
    public Identity saveTemporary(CommitContext context, Identity client) {
        try {
            dataManager.commit(context);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dataManager.reload(client, "identity-view");

    }
}
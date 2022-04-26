package com.company.arclab.service;

import com.company.arclab.entity.client.Identity;
import com.haulmont.cuba.core.TransactionalDataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(ClientSearchService.NAME)
public class ClientSearchServiceBean implements ClientSearchService {

    @Inject
    private TransactionalDataManager transactionalDataManager;

    @Override
    public List<Identity> findAllActiveClientsByIinBin(String iinBin) {
        return transactionalDataManager.load(Identity.class)
                .query("select c from arclab_Identity c where c.iinBin= :iin" +
                        " and c.status <> 'New' and c.status <> 'Modify'")
                .parameter("iin", iinBin)
                .view("identity-view")
                .list();
    }

    @Override
    public List<Identity> findAllActiveClientsByName(String name) {
        return transactionalDataManager.load(Identity.class)
                .query("select c from arclab_Identity c where c.fullName= :name" +
                        " and c.status <> 'New' and c.status <> 'Modify'")
                .parameter("name", name)
                .view("identity-view")
                .list();
    }
}
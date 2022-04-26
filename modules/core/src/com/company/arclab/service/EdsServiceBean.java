package com.company.arclab.service;

import com.company.arclab.entity.kalkan.EdsRegistry;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service(EdsService.NAME)
public class EdsServiceBean implements EdsService {

    @Inject
    private DataManager dataManager;
    @Inject
    private KalkanSignVerifyService kalkanSignVerifyService;

    @Override
    public List<EdsRegistry> loadFormDb(Entity entity) {
        List<EdsRegistry> edsRegistryList = new ArrayList<>();
        for (int i = 0; i < edsRegistryList.size(); i++) {
            edsRegistryList.set(i, kalkanSignVerifyService.isGoodSignature(edsRegistryList.get(i)));
        }
        return edsRegistryList;
    }

}
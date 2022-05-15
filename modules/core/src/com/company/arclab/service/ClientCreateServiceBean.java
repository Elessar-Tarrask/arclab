package com.company.arclab.service;

import com.company.arclab.entity.application.EClientApplicationType;
import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.TManager;
import com.company.arclab.entity.client.dict.EClientStatus;
import com.company.arclab.entity.client.dict.EClientType;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(ClientCreateService.NAME)
public class ClientCreateServiceBean implements ClientCreateService {

    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private TransactionalDataManager transactionalDataManager;
    @Inject
    private Logger log;

    @Override
    public void findManager() {
        //TODO
    }

    @Override
    public void finishClientApplication() {
        //TODO
    }

    @Override
    public Map<IdentityApplication, String> startClientApplication(String iinBin, String fullname, String clientType, String appType, User user, String processKey) {
        Map<IdentityApplication, String> clientAppMap = new HashMap<>();
        final String clientAppNew = "clientAppNew";
        Identity client;
        client = metadata.create(Identity.class);
        client.setFullName(fullname);
        client.setClientType(EClientType.fromId(clientType));
        client.setIinBin(iinBin);
        client.setStatus(EClientStatus.NEW);
        client.setRegDate(new Date());
        client = dataManager.commit(client);

        IdentityApplication identityApplication = metadata.create(IdentityApplication.class);
        Long reqNum = uniqueNumbersAPI.getNextNumber("applicationSequence");
        identityApplication.setFullName(fullname);
        identityApplication.setIinBin(iinBin);
        identityApplication.setReqId(reqNum);
        identityApplication.setName(identityApplication.getMetaClass().getName());
        identityApplication.setApplicationType(EClientApplicationType.fromId(appType));
        TManager manager = dataManager.load(TManager.class).id(user.getId()).view("tManager-view")
                .optional().orElse(null);
        if (manager != null)
            identityApplication.setManager(manager);
        identityApplication.setIdentity(client);
        identityApplication = dataManager.commit(identityApplication);

        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("iinBin", iinBin);
        processVariables.put("fullname", fullname);
        processVariables.put("clientType", clientType);
        processVariables.put("frontOffice", user);
        processVariables.put("appType", appType);
        processVariables.put("existsAml", null);
        processVariables.put("procId", null);
        processVariables.put("client", client);
        processVariables.put("appId", identityApplication.getId().toString());
        processVariables.put("reqNum", reqNum);
        processVariables.put("clientApplication", identityApplication);
        processVariables.put("application", identityApplication);
        ProcessInstanceData processInstanceData = null;

        try {
            processInstanceData = bprocRuntimeService.startProcessInstanceByKey(processKey,
                    "Заведение нового клиента",
                    processVariables);
            bprocRuntimeService.setVariable(processInstanceData.getId(), "procId",
                    processInstanceData.getId());

            identityApplication = dataManager.reload(identityApplication, "identityApplication-view");
            identityApplication.setProcId(processInstanceData.getId());

            identityApplication = transactionalDataManager.save(identityApplication);
            clientAppMap.put(identityApplication, clientAppNew);
            return clientAppMap;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                if (processInstanceData != null)
                    bprocRuntimeService.deleteProcessInstance(processInstanceData.getId(),
                            "Error in client Application creation");
                if (identityApplication != null)
                    dataManager.remove(identityApplication);
            } catch (Exception ignore) {
            }
            return null;
        }
    }


}
package com.company.arclab.service;

import com.company.arclab.entity.application.IdentityApplication;
import com.haulmont.cuba.security.entity.User;

import java.util.Map;

public interface ClientCreateService {
    String NAME = "arclab_ClientCreateService";

    void findManager();

    void finishClientApplication();

    Map<IdentityApplication, String> startClientApplication(String iinBin, String fullname, String clientType, String appType, User user,
                                                            String processKey);

}
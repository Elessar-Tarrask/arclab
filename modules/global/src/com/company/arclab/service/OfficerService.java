package com.company.arclab.service;

import com.company.arclab.entity.client.TManager;
import com.haulmont.cuba.security.entity.User;

import java.util.UUID;

public interface OfficerService {
    String NAME = "arclab_OfficerService";

    User getUserById(UUID userId);

    TManager getOfficerByUser(User user);
}
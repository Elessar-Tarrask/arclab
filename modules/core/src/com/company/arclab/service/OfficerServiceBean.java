package com.company.arclab.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(OfficerService.NAME)
public class OfficerServiceBean implements OfficerService {

    @Inject
    private DataManager dataManager;

    @Override
    public User getUserById(UUID userId) {
        return dataManager.load(User.class).id(userId).view("user-view").optional().orElse(null);
    }
}
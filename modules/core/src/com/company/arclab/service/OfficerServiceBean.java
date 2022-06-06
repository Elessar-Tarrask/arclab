package com.company.arclab.service;

import com.company.arclab.entity.client.TManager;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(OfficerService.NAME)
public class OfficerServiceBean implements OfficerService {

    @Inject
    private DataManager dataManager;
    @Inject
    private TransactionalDataManager transactionalDataManager;

    @Override
    public User getUserById(UUID userId) {
        return dataManager.load(User.class).id(userId).view("user-view").optional().orElse(null);
    }

    public TManager getOfficerByUser(User user) {
        if (user == null)
            return null;
        TManager officer = null;
        try {
            officer = transactionalDataManager.load(TManager.class)
                    .query("select o from arclab_TManager o where o.id = :id")
                    .parameter("id", user.getId())
                    .view("officer-view")
                    .one();
        } catch (IllegalStateException e) {
            if (officer == null) throw new IllegalArgumentException("No such officer with user_id " + user.getId());
        }
        return officer;
    }
}
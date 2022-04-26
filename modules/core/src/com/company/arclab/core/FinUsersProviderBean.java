package com.company.arclab.core;

import com.company.arclab.core.role.FinControllerRole;
import com.haulmont.addon.bproc.provider.UserListProvider;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component(FinUsersProviderBean.NAME)
public class FinUsersProviderBean implements UserListProvider {
    public static final String NAME = "arclab_FinUsersProviderBean";
    @Inject
    private DataManager dataManager;
    @Inject
    private Logger log;

    @Override
    public List<User> get(String executionId) {
        List<User> users = dataManager.load(User.class)
                .query("select c from sec$User c, sec$UserRole ur WHERE " +
                        "c.id = ur.user.id and ur.roleName = :role")
                .parameter("role", FinControllerRole.NAME).list();
        if (users != null && !users.isEmpty()) {
            log.info("finControllers for exectionId - " + executionId);
            users.stream().filter(u -> u != null && u.getLogin() != null)
                    .forEach(u -> log.info("user - " + u.getLogin()));
        }
        return users;
    }

    @Override
    public List<String> getForProcess(String executionId) {
        return UserListProvider.super.getForProcess(executionId);
    }
}
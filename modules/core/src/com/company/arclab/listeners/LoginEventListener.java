package com.company.arclab.listeners;

import com.company.arclab.service.ActiveApplicationService;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.auth.events.UserLoggedInEvent;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@ManagedBean(LoginEventListener.NAME)
public class LoginEventListener {

    private final List<String> excluded = Arrays.asList("anonymous", "admin", "rest");

    public static final String NAME = "arclab_LoginEventListener";
    @Inject
    private ActiveApplicationService activeApplicationService;
    @Inject
    private Logger log;

    @EventListener
    public void userLoggedIn(UserLoggedInEvent event) {
//        final UserSession userSession = event.getUserSession();
//        if (userSession.isSystem())
//            return;
//        AppContext.withSecurityContext(new SecurityContext(userSession), () -> {
//            User user = event.getUserSession().getUser();
//            if(!excluded.contains(user.getLogin())) {
//                //userUpdateService.updateFromEsb(user, user.getLogin());
//            }
//            notifyAboutTasks(user);
//        });
        log.trace("UserLoggen in ", event.getAuthenticationDetails());
    }

    private void notifyAboutTasks(User user) {
        activeApplicationService.publishTasksCount(user);
    }


}
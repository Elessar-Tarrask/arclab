package com.company.arclab.service;

import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(ActiveApplicationService.NAME)
public class ActiveApplicationServiceBean implements ActiveApplicationService {

    @Override
    public List<TaskData> getActiveTasks(User user) {
        return null;
    }

    @Override
    public void publishTasksCount(User user) {

    }
}
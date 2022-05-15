package com.company.arclab.service;

import com.company.arclab.entity.client.Identity;
import com.haulmont.cuba.core.global.CommitContext;

public interface ClientService {
    String NAME = "arclab_ClientService";

    Identity saveTemporary(CommitContext commitContext, Identity client);
}
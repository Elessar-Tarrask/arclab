package com.company.arclab.listeners;

import com.company.arclab.entity.application.Application;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.UUID;

@Component("arclab_ApplicationChangedListener")
public class ApplicationChangedListener {

    @Inject
    private TransactionalDataManager tx;
    @Inject
    private UniqueNumbersAPI uniqueNumbers;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(EntityChangedEvent<Application, UUID> event) {
        Application application;
        if (event.getType() == EntityChangedEvent.Type.CREATED) {
            application = tx.load(event.getEntityId())
                    .view("_local")
                    .optional()
                    .orElse(null);
            if (application != null && application.getReqId() == null) {
                application.setReqId(uniqueNumbers.getNextNumber("applicationSequence"));
                tx.save(application);
            }
        }
    }
}
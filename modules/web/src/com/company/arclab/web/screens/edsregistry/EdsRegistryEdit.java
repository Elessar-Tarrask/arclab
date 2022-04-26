package com.company.arclab.web.screens.edsregistry;

import com.company.arclab.web.screens.kalkan.KalkanCryptSignXmlFragment;
import com.company.arclab.web.screens.kalkan.KalkanSignaturesListTable;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.UploadField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.arclab.entity.kalkan.EdsRegistry;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.File;

@UiController("arclab_EdsRegistry.edit")
@UiDescriptor("eds-registry-edit.xml")
@EditedEntityContainer("edsRegistryDc")
@LoadDataBeforeShow
public class EdsRegistryEdit extends StandardEditor<EdsRegistry> {
    @Inject
    private KalkanCryptSignXmlFragment ncaLayerFragment;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Notifications notifications;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private TextField<String> commentTextField;
    @Inject
    private KalkanSignaturesListTable signatoryListFragment;
    @Inject
    private DataContext dataContext;
    @Inject
    private InstanceContainer<EdsRegistry> edsRegistryDc;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        signatoryListFragment.setEntityId(null);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        dataContext.evict(edsRegistryDc.getItem());
    }

    @Subscribe("uploadField")
    public void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) throws Exception {
        File file = fileUploadingAPI.getFile(uploadField.getFileId());
        if (file != null) {
            notifications.create()
                    .withCaption("File is uploaded to temporary storage at " + file.getAbsolutePath())
                    .show();
        }

        FileDescriptor fd = uploadField.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(uploadField.getFileId(), fd);
        } catch (FileStorageException e) {
            throw new RuntimeException("Error saving file to FileStorage", e);
        }

        ncaLayerFragment.setInfoToSign(fd, null, "Sign", commentTextField.getValue());
        dataManager.commit(fd);
    }

    @Subscribe("uploadField")
    public void onUploadFieldFileUploadError(UploadField.FileUploadErrorEvent event) {
        notifications.create()
                .withCaption("File upload error")
                .show();
    }
}
package com.company.arclab.core.role;

import com.company.arclab.entity.application.Application;
import com.company.arclab.entity.application.IdentityApplication;
import com.company.arclab.entity.client.DocFormed;
import com.company.arclab.entity.client.Identity;
import com.company.arclab.entity.client.TClientAddress;
import com.company.arclab.entity.client.TContactPhone;
import com.company.arclab.entity.client.TManager;
import com.company.arclab.entity.client.acts.TWeatherConditions;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.FilterEntity;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = ManagerRole.NAME, description = "Менеджер")
public class ManagerRole extends AnnotatedRoleDefinition {
    public final static String NAME = "Manager";

    public static final String LOC_NAME = "Менеджер";

    @Override
    public String getLocName() {
        return LOC_NAME;
    }

    @ScreenAccess(screenIds = {"tasks"})
    @ScreenAccess(screenIds = {"acts"})
    @ScreenAccess(screenIds = {"arclab_IdentitySerachScreen"})
    @ScreenAccess(screenIds = {"contracts"})
    @ScreenAccess(screenIds = {"arclab_IdentityApplication.browse"})
    @ScreenAccess(screenIds = {"arclab_CreateClient"})
    @ScreenAccess(screenIds = {"jcrm_DocFormed.browse"})
    @ScreenAccess(screenIds = {"arclab_EdsRegistry.browse"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }


    @EntityAttributeAccess(entityClass = Application.class, modify = "*")
    @EntityAttributeAccess(entityClass = IdentityApplication.class, modify = "*")
    @EntityAttributeAccess(entityClass = TManager.class, modify = "*")
    @EntityAttributeAccess(entityClass = Identity.class, modify = "*")
    @EntityAttributeAccess(entityClass = TWeatherConditions.class, modify = "*")
    @EntityAttributeAccess(entityClass = TClientAddress.class, modify = "*")
    @EntityAttributeAccess(entityClass = TContactPhone.class, modify = "*")
    @EntityAttributeAccess(entityClass = DocFormed.class, modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }

    @EntityAccess(entityClass = IdentityApplication.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = TManager.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = FilterEntity.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Identity.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = TClientAddress.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = TWeatherConditions.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = TContactPhone.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @EntityAccess(entityClass = DocFormed.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }
}

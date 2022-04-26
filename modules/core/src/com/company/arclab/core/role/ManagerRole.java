package com.company.arclab.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.Role;

@Role(name = ManagerRole.NAME, description = "Менеджер")
public class ManagerRole extends AnnotatedRoleDefinition {
    public final static String NAME = "Manager";
}

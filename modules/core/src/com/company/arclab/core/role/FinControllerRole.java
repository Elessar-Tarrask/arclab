package com.company.arclab.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.Role;

@Role(name = FinControllerRole.NAME, description = "Руководитель")
public class FinControllerRole extends AnnotatedRoleDefinition {
    public final static String NAME = "FinController";
}

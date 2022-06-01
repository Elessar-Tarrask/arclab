package com.company.arclab.service;

import com.company.arclab.entity.kalkan.EdsRegistry;
import com.haulmont.cuba.core.entity.Entity;

import java.util.List;

public interface EdsService {
    String NAME = "arclab_EdsService";

    List<EdsRegistry> loadFormDb(String checkSum);
}
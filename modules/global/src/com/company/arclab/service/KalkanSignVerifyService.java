package com.company.arclab.service;

import com.company.arclab.entity.kalkan.EdsRegistry;

import java.util.List;

public interface KalkanSignVerifyService {
    String NAME = "arclab_KalkanSignVerifyService";

    List<EdsRegistry> verifyXml(String xmlString);

    EdsRegistry isGoodSignature(EdsRegistry edsRegistry);
}
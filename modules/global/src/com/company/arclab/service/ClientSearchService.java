package com.company.arclab.service;

import com.company.arclab.entity.client.Identity;

import java.util.List;

public interface ClientSearchService {
    String NAME = "arclab_ClientSearchService";

    List<Identity> findAllActiveClientsByIinBin(String iinBin);
    List<Identity> findAllActiveClientsByName(String name);
}
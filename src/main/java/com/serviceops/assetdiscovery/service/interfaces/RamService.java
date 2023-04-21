package com.serviceops.assetdiscovery.service.interfaces;

import com.serviceops.assetdiscovery.rest.RamRest;

import java.util.List;

public interface RamService {
    void save(long id);

    RamRest findByRefId(long refId);

    List<RamRest> findAllByRefId(long refId);

    RamRest update(long refId, long id, RamRest ramRest);

    boolean deleteById(long refId, long id);
}

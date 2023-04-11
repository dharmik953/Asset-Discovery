package com.serviceops.assetdiscovery.service.interfaces;
import com.serviceops.assetdiscovery.rest.RamRest;

import java.util.List;

public interface RamService {
    void save(Long id);

    RamRest findByRefId(Long refId);
    List<RamRest> findAllByRefId(Long refId);

    void deleteById(Long refId);

    void update(RamRest ramRest);
}
package com.aukcje.service.iface;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.entity.PurchaseStatus;

import java.util.List;
import java.util.Optional;

public interface PurchaseStatusService {

    PurchaseStatusDTO findById(Integer id);

    List<PurchaseStatusDTO> findAll();

}

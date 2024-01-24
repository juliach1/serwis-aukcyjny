package com.aukcje.service.iface;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.exception.PurchaseStatusNotFoundException;

import java.util.List;

public interface PurchaseStatusService {

    PurchaseStatusDTO findById(Integer id) throws PurchaseStatusNotFoundException;

    List<PurchaseStatusDTO> findAll();

}

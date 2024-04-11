package com.aukcje.service.iface;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.exception.customException.PurchaseStatusNotFoundException;

import java.util.List;

public interface PurchaseStatusService {

    PurchaseStatusDTO findById(Integer id) throws PurchaseStatusNotFoundException;

    List<PurchaseStatusDTO> findAll();

}

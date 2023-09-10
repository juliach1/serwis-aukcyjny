package com.aukcje.service.iface;

import com.aukcje.dto.UserStatusDTO;
import com.aukcje.entity.PurchaseStatus;

public interface PurchaseStatusService {

    PurchaseStatus findById(Integer id);

}

package com.aukcje.service.iface;

import com.aukcje.dto.ItemConditionDTO;
import com.aukcje.entity.ItemCondition;

import java.util.List;

public interface ItemConditionService {

    List<ItemConditionDTO> findAll();

    ItemCondition findByName(String name);
}

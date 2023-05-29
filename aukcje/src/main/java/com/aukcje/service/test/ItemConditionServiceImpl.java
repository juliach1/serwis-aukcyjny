package com.aukcje.service.test;

import com.aukcje.dto.ItemConditionDTO;
import com.aukcje.dto.mapper.ItemConditionDTOMapper;
import com.aukcje.entity.ItemCondition;
import com.aukcje.repository.ItemConditionRepository;
import com.aukcje.service.iface.ItemConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemConditionServiceImpl implements ItemConditionService {

    @Autowired
    ItemConditionRepository itemConditionRepository;

    @Override
    public List<ItemConditionDTO> findAll() {
        return getItemConditionDTO(itemConditionRepository.findAll());
    }

    @Override
    public ItemCondition findByName(String name) {
        return itemConditionRepository.findByName(name);
    }

    private ItemConditionDTO getItemConditionDTO(ItemCondition itemCondition){
        return ItemConditionDTOMapper.instance.itemConditionDTO(itemCondition);
    }

    private List<ItemConditionDTO> getItemConditionDTO(List<ItemCondition> itemConditions){
       List<ItemConditionDTO> itemConditionDTOS = new ArrayList<>();

        for (ItemCondition itemCondition : itemConditions){
            itemConditionDTOS.add(ItemConditionDTOMapper.instance.itemConditionDTO(itemCondition));
        }

        return itemConditionDTOS;
    }
}

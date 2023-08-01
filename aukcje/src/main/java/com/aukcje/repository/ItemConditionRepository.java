package com.aukcje.repository;

import com.aukcje.entity.ItemCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemConditionRepository  extends JpaRepository<ItemCondition, Integer> {

    ItemCondition findByName(String name);

}

package com.aukcje.repository;

import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.entity.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferTypeRepository extends JpaRepository<OfferType, Integer> {

}

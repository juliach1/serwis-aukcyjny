package com.aukcje.service.iface;

import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.entity.OfferType;
import com.aukcje.repository.OfferTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface OfferTypeService {

    List<OfferTypeDTO> findAll();

    List<String> getAllNames();
}

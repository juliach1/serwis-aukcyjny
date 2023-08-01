package com.aukcje.service;

import com.aukcje.dto.OfferDetailsDTO;
import com.aukcje.dto.mapper.OfferDetailsDTOMapper;
import com.aukcje.entity.OfferDetails;
import com.aukcje.repository.OfferDetailsRepository;
import com.aukcje.service.iface.OfferDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferDetailsServiceImpl implements OfferDetailsService {

    @Autowired
    OfferDetailsRepository offerDetailsRepository;


    private OfferDetailsDTO createOfferDetailsDTO(OfferDetails offerDetails){
        return OfferDetailsDTOMapper.instance.offerDetailsDTO(offerDetails);
    }
}

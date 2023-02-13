package com.aukcje.service;

import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.dto.mapper.OfferTypeDTOMapper;
import com.aukcje.entity.OfferType;
import com.aukcje.repository.OfferTypeRepository;
import com.aukcje.service.iface.OfferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferTypeServiceImpl implements OfferTypeService {

    @Autowired
    private OfferTypeRepository offerTypeRepository;


    @Override
    public List<OfferTypeDTO> findAll() {
        List<OfferType> offerTypes = offerTypeRepository.findAll();

        System.out.println(offerTypes);
        return createOfferTypeDTO(offerTypes);
    }

    @Override
    public List<String> getAllNames() {

        List<OfferTypeDTO> offerTypeDTOS = findAll();
        List<String> offerTypesNames = new ArrayList<>();

        for (OfferTypeDTO offerTypeDTO : offerTypeDTOS){
            offerTypesNames.add(offerTypeDTO.getName());
        }

        return offerTypesNames;
    }

    private OfferTypeDTO createOfferTypeDTO(OfferType offerType){
        return OfferTypeDTOMapper.instance.offerTypeDTO(offerType);
    }

    private List<OfferTypeDTO> createOfferTypeDTO(List<OfferType> offerTypes){
        List<OfferTypeDTO> offerTypeDTOS = new ArrayList<>();

        for(OfferType offerType : offerTypes){
            offerTypeDTOS.add(OfferTypeDTOMapper.instance.offerTypeDTO(offerType));
            System.out.println("offertype: "+offerType.getName());
        }

        return offerTypeDTOS;
    }
}

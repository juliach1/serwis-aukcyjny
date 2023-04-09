package com.aukcje.service;

import com.aukcje.dto.CategoryPathCategoryDTO;
import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferDetailsDTO;
import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.dto.mapper.OfferDTOMapper;
import com.aukcje.entity.Category;
import com.aukcje.entity.Offer;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.repository.CustomOfferRepository;
import com.aukcje.repository.OfferRepository;
import com.aukcje.service.iface.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferPhotoService offerPhotoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomOfferRepository customOfferRepository;

    private final Integer DEFAULT_PAGE_SIZE = 12;


    @Override
    public OfferDTO findById(Long id) {
        OfferDTO offerDTO = createOfferDTO( offerRepository.findById(id).orElse(new Offer()) );
        offerDTO.setOfferPhotoDTO( offerPhotoService.findByOfferId(id) );



        return offerDTO;
    }

    @Override
    public List<OfferDTO> findNewAuctions(Integer pageSize) {
        return findNewByOfferTypeId(2, pageSize);
    }

    @Override
    public List<OfferDTO> findNewBuyNow(Integer pageSize) {
        return findNewByOfferTypeId(1, pageSize);
    }

    @Override
    public List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel) {
        return createOfferDTO(customOfferRepository.findByOfferSearchModel(offerSearchModel).toList());
    }

    @Override
    public Boolean isOfferTypeAuction(OfferDTO offer) {
        return OfferTypeEnum.AUKCJA.toString().equals(offer.offerType.getName());
    }

    private List<OfferDTO> findNewByOfferTypeId(Integer offerTypeId, Integer pageSize) {
        if(isInvalid(pageSize)) pageSize = DEFAULT_PAGE_SIZE;

        List<Offer> offers = offerRepository.findNewByOfferTypeId( offerTypeId, setPageSize(pageSize) ).toList() ;
        List<OfferDTO> offerDTOS = createOfferDTO( offers );


        return offerDTOS;
    }

    private Boolean isInvalid(Integer pageSize){
        return pageSize == null || pageSize<=0;
    }
    private Pageable setPageSize(Integer size){
        return PageRequest.of(0, size);
    }


    private OfferDTO createOfferDTO(Offer offer){
        OfferDTO offerDTO = OfferDTOMapper.instance.offerDTO(offer);
        Category category = offer.getCategory();

        List<CategoryPathCategoryDTO> categoryPathCategoryDTOS = categoryService.getCategoryPath(category);

        offerDTO.setCategoryPath(categoryPathCategoryDTOS);

        return offerDTO ;
    }

    private List<OfferDTO> createOfferDTO(List<Offer> offers){
        List<OfferDTO> offerDTOS = new ArrayList<>();

        for(Offer offer : offers){
            offerDTOS.add( createOfferDTO(offer) );
        }
        return offerDTOS;
    }


}

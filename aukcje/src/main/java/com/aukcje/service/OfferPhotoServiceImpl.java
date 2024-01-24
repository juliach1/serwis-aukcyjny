package com.aukcje.service;

import com.aukcje.dto.OfferPhotoDTO;
import com.aukcje.dto.mapper.OfferPhotoDTOMapper;
import com.aukcje.entity.OfferPhoto;
import com.aukcje.repository.OfferPhotoRepository;
import com.aukcje.service.iface.OfferPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class OfferPhotoServiceImpl implements OfferPhotoService {

    private final OfferPhotoRepository offerPhotoRepository;

    @Override
    public List<OfferPhotoDTO> findByOfferId(Long offerId) {
        return createOfferPhotoDTO( offerPhotoRepository.getByOfferId(offerId) );
    }

    @Override
    public void deleteByOfferId(Long offerId) {
        offerPhotoRepository.deleteByOfferId(offerId);
    }

    private OfferPhotoDTO createOfferPhotoDTO(OfferPhoto offerPhoto){
        return OfferPhotoDTOMapper.instance.offerPhotoDTO(offerPhoto);
    }

    private List<OfferPhotoDTO> createOfferPhotoDTO(List<OfferPhoto> offerPhotos) {
        List<OfferPhotoDTO> offerPhotoDTOS = new ArrayList<>();

        for(OfferPhoto offerPhoto : offerPhotos){
            offerPhotoDTOS.add( createOfferPhotoDTO(offerPhoto) );
        }
        return offerPhotoDTOS;
    }
}

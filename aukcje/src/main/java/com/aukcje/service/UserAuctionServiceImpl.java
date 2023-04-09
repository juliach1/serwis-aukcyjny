package com.aukcje.service;

import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.dto.mapper.UserAuctionDTOMapper;
import com.aukcje.entity.Offer;
import com.aukcje.entity.UserAuction;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserAuctionRepository;
import com.aukcje.service.iface.UserAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuctionServiceImpl implements UserAuctionService {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserAuctionRepository userAuctionRepository;

    @Override
    public List<UserAuctionDTO> findAllByOfferId(Long offerId) {
        Offer currentOffer = offerRepository.getOne(offerId);
        List<UserAuction> userAuctions = userAuctionRepository.findAllByOfferId(currentOffer.getId());


        return createUserAuctionDTO(userAuctions);
    }

    @Override
    public UserAuctionDTO findOneWithHighestValue(Long offerId) {
        Offer currentOffer = offerRepository.getOne(offerId);
        UserAuction userAuction = userAuctionRepository.findFirstByOfferOrderByValueDesc(currentOffer);

        return createUserAuctionDTO(userAuction);
    }

    private UserAuctionDTO createUserAuctionDTO(UserAuction userAuction){
        return UserAuctionDTOMapper.instance.userAuctionDTO(userAuction);
    }

    private List<UserAuctionDTO> createUserAuctionDTO(List<UserAuction> userAuctions){
        List<UserAuctionDTO> userAuctionDTOS = new ArrayList<>();
        for(UserAuction userAuction : userAuctions){
            userAuctionDTOS.add(UserAuctionDTOMapper.instance.userAuctionDTO(userAuction));
        }
        return userAuctionDTOS;
    }
}

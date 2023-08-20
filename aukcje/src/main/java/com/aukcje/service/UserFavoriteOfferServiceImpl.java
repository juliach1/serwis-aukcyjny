package com.aukcje.service;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.dto.mapper.UserFavoriteOfferDTOMapper;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserFavoriteOffer;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserFavoriteOfferRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.UserFavoriteOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserFavoriteOfferServiceImpl implements UserFavoriteOfferService {

    @Autowired
    private UserFavoriteOfferRepository userFavoriteOfferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<UserFavoriteOfferDTO> getAllByUserId(Long userId) {
        return createUserFavoriteOfferDTO(userFavoriteOfferRepository.findByUserId(userId));
    }

    @Override
    public void add(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);

        UserFavoriteOffer userFavoriteOffer = new UserFavoriteOffer();
        userFavoriteOffer.setUser(user);
        userFavoriteOffer.setOffer(offer);
        userFavoriteOffer.setInsertTime(LocalDateTime.now());

        System.out.println("SAVED OFFER: ");
        System.out.println("USER ID "+userFavoriteOffer.getUser().getId());
        System.out.println("OFFER ID "+userFavoriteOffer.getOffer().getId());
        System.out.println("INSERT DATE "+userFavoriteOffer.getInsertTime());

        userFavoriteOfferRepository.save(userFavoriteOffer);
    }

    @Override
    public void remove(Long offerId, Long userId) {
        UserFavoriteOffer favoriteOffer = userFavoriteOfferRepository.findByUserIdAndOfferId(offerId, userId);
        if(favoriteOffer != null){
            userFavoriteOfferRepository.deleteByUserIdAndOfferId(userId, offerId);
        }
    }

    private List<UserFavoriteOfferDTO> createUserFavoriteOfferDTO(List<UserFavoriteOffer> userFavoriteOffers){
        List<UserFavoriteOfferDTO> userFavoriteOfferDTOS = new ArrayList<>();
        for (UserFavoriteOffer userFavoriteOffer : userFavoriteOffers){
            userFavoriteOfferDTOS.add( createUserFavoriteOfferDTO(userFavoriteOffer) );
        }
        return userFavoriteOfferDTOS;
    }
    private UserFavoriteOfferDTO createUserFavoriteOfferDTO(UserFavoriteOffer userFavoriteOffer){
        return UserFavoriteOfferDTOMapper.instance.userFavoriteOfferDTO(userFavoriteOffer);
    }
}

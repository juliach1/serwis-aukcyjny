package com.aukcje.service;

import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.dto.mapper.UserFavoriteOfferDTOMapper;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserFavoriteOffer;
import com.aukcje.enums.OfferStatusEnum;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserFavoriteOfferRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.UserFavoriteOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class UserFavoriteOfferServiceImpl implements UserFavoriteOfferService {

    private final UserFavoriteOfferRepository userFavoriteOfferRepository;
    private final UserRepository userRepository;
    private final  OfferRepository offerRepository;

    @Override
    public List<UserFavoriteOfferDTO> getActiveByUserId(Long userId, Integer pageSize) {
        List<UserFavoriteOffer> offers = userFavoriteOfferRepository.findByUserIdAndOfferOfferStatusIdOrderByInsertTimeDesc( userId, OfferStatusEnum.ACTIVE.getId(), setPageSize(pageSize) ).toList() ;
        return createUserFavoriteOfferDTO(offers);
    }

    @Override
    public UserFavoriteOfferDTO geByUserIdAndOfferId(Long userId, Long offerId) {
        System.out.println(createUserFavoriteOfferDTO(userFavoriteOfferRepository.findByUserIdAndOfferId(userId, offerId)));
        return createUserFavoriteOfferDTO(userFavoriteOfferRepository.findByUserIdAndOfferId(userId, offerId));
    }

    @Override
    @Transactional
    public void deleteAllByOfferId(Long offerId) {
        userFavoriteOfferRepository.deleteByOfferId(offerId);
    }

    @Override
    @Transactional
    public void toggle(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException {
        UserFavoriteOffer userFavoriteOffer = userFavoriteOfferRepository.findByUserIdAndOfferId(userId, offerId);

        if(userFavoriteOffer!=null)
            delete(offerId, userId);
        else
            add(offerId, userId);
    }

    @Override
    public void add(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);

        if(userFavoriteOfferRepository.findByUserIdAndOfferId(userId, offerId)==null){
            UserFavoriteOffer userFavoriteOffer = new UserFavoriteOffer();
            userFavoriteOffer.setUser(user);
            userFavoriteOffer.setOffer(offer);
            userFavoriteOffer.setInsertTime(LocalDateTime.now());
            userFavoriteOfferRepository.save(userFavoriteOffer);
        }

    }

    private void delete(Long offerId, Long userId) {
        userFavoriteOfferRepository.deleteByUserIdAndOfferId(userId, offerId);
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

    private Pageable setPageSize(Integer size){
        return PageRequest.of(0, size);
    }
}

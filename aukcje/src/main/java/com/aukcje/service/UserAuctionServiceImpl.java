package com.aukcje.service;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.dto.mapper.UserAuctionDTOMapper;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserAuction;
import com.aukcje.enums.BidStatusEnum;
import com.aukcje.enums.OfferStatusEnum;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.customException.CanNotBidYourOfferException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotActiveException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserAuctionRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserAuctionService;
import com.aukcje.service.iface.UserService;
import com.aukcje.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAuctionServiceImpl implements UserAuctionService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public BidStatusEnum placeBid(Long offerId, Double value, Long userId) throws OfferNotFoundException, OfferNotActiveException, UserNotFoundException, UserNotActiveException, CanNotBidYourOfferException {
        UserAuction userAuction = new UserAuction();

        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getId(), offer.getUser().getId())){
            throw new CanNotBidYourOfferException();
        }

        if(!userService.isUserActive(user)){
            throw new UserNotActiveException();
        }

        if(!offerService.isOfferActive(offer)){
            throw new OfferNotActiveException(offer.getOfferDetails().getTitle());
        }

        if(Utils.isDateBeforeNow(offer.getEndDate())){
            return BidStatusEnum.AUCTION_ENDED;
        }
        userAuction.setInsertTime(LocalDateTime.now());

        if (isHigherThanLastBid(value, offerId)){
            return BidStatusEnum.TOO_LOW;
        }

        userAuction.setUser(user);
        userAuction.setOffer(offer);
        userAuction.setValue(value);
        userAuctionRepository.save(userAuction);

        return BidStatusEnum.BID_PLACED;
    }

    private boolean isHigherThanLastBid(Double value, Long offerId){
        UserAuctionDTO oneWithHighestValue = findOneWithHighestValue(offerId);
        return oneWithHighestValue.getValue() >= value;
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

package com.aukcje.service;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.dto.mapper.UserAuctionDTOMapper;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserAuction;
import com.aukcje.enums.BidStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.customException.*;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserAuctionRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserAuctionService;
import com.aukcje.service.iface.UserService;
import com.aukcje.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class UserAuctionServiceImpl implements UserAuctionService {

    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserAuctionRepository userAuctionRepository;

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
    public BidStatusEnum placeBid(Long offerId, Double value, Long userId) throws OfferNotFoundException, OfferNotActiveException, UserNotFoundException, UserNotActiveException, CanNotBidYourOfferException, OfferStatusNotFoundException {
        UserAuction userAuction = new UserAuction();

        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);

        User user  = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
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

    @Override
    public boolean hasUserAnyAuctions(OfferDTO offerDTO) {
        return findAllByOfferId(offerDTO.getId()).size()>0;
    }

    private boolean isHigherThanLastBid(Double value, Long offerId) throws OfferNotFoundException, OfferStatusNotFoundException {
        UserAuctionDTO oneWithHighestValue = findOneWithHighestValue(offerId);
        if(oneWithHighestValue==null){
            OfferDTO offerDTO = offerService.findById(offerId);
            System.out.println(offerDTO.getPrice() <= value);
            return offerDTO.getPrice() >= value;
        }
        return oneWithHighestValue.getValue() >= value;
    }

    private UserAuctionDTO createUserAuctionDTO(UserAuction userAuction) {
        return UserAuctionDTOMapper.instance.userAuctionDTO(userAuction);
    }

    private List<UserAuctionDTO> createUserAuctionDTO(List<UserAuction> userAuctions) {
        List<UserAuctionDTO> userAuctionDTOS = new ArrayList<>();
        for(UserAuction userAuction : userAuctions){
            userAuctionDTOS.add(UserAuctionDTOMapper.instance.userAuctionDTO(userAuction));
        }
        return userAuctionDTOS;
    }
}

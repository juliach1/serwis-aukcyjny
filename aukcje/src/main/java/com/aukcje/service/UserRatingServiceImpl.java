package com.aukcje.service;

import com.aukcje.dto.UserRatingDTO;
import com.aukcje.dto.mapper.UserRatingDTOMapper;
import com.aukcje.entity.User;
import com.aukcje.entity.UserRating;
import com.aukcje.exception.customException.CouldNotRateUser;
import com.aukcje.exception.customException.PurchaseNotFoundException;
import com.aukcje.model.UserRatingModel;
import com.aukcje.repository.UserRatingRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.UserRatingService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;
    private final OfferPurchaseService offerPurchaseService;
    private final UserService userService;

    @Override
    public Double calculateAvarageRatingForUser(Long userId) {
        List<UserRating> userRatings = userRatingRepository.getAllByUserId(userId);
        List<UserRatingDTO> userRatingDTOS = createUserRatingDTO(userRatings);

        int ratingsNumber = userRatingDTOS.size();
        long ratingsSum = 0L;
        Double avarageValue = null;

        if(ratingsNumber >= 5) {
            for (UserRatingDTO tempRating : userRatingDTOS) {
                ratingsSum = ratingsSum + tempRating.getRating();
            }
            avarageValue = 1.0 * ratingsSum / ratingsNumber;
        }
        return avarageValue;
    }

    @Override
    public void addRatingForUser(UserRatingModel userRatingModel) throws CouldNotRateUser, PurchaseNotFoundException {

        if(isValid(userRatingModel)){
            long userId = userRatingModel.getUserId();
            long purchaseId = userRatingModel.getPurchaseId();
            int rating = userRatingModel.getRating();
            User user = userRepository.getOne(userId);

            UserRating userRating = new UserRating();
            userRating.setUser(user);
            userRating.setRating(rating);

            UserRating userRatingNew = userRatingRepository.save(userRating);
            offerPurchaseService.setRating(purchaseId, userRatingNew);

            Double avarageRate = calculateAvarageRatingForUser(userId);
            userService.setUserRating(userRatingModel.getUserId(), avarageRate);
        }else
            throw new CouldNotRateUser();
    }

    @Override
    public Long getRatingsNumberByUserId(Long userId) {
        return userRatingRepository.countByUserId(userId);
    }

    private boolean isValid(UserRatingModel userRatingModel) {
        Integer rating = userRatingModel.getRating();
        Long userId = userRatingModel.getUserId();
        return userId != null && rating >= 1  && rating <= 5 && userRatingModel.getPurchaseId() != null;
    }

    private UserRatingDTO createUserRatingDTO(UserRating userRating) {
        return UserRatingDTOMapper.instance.userRatingDTO(userRating);
    }

    private List<UserRatingDTO> createUserRatingDTO(List<UserRating> userRatings) {
        List<UserRatingDTO> userRatingDTOS = new ArrayList<>();

        for (UserRating userRating : userRatings){
            UserRatingDTO userRatingDTO = createUserRatingDTO(userRating);
            userRatingDTOS.add(userRatingDTO);
        }
        return userRatingDTOS;
    }
}

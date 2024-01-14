package com.aukcje.service.iface;

import com.aukcje.exception.customException.CouldNotRateUser;
import com.aukcje.exception.customException.PurchaseNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.UserRatingModel;

public interface UserRatingService {

    void addRatingForUser(UserRatingModel userRatingModel) throws CouldNotRateUser, PurchaseNotFoundException;

    Long getRatingsNumberByUserId(Long userId);

    Double calculateAvarageRatingForUser(Long userId);
}

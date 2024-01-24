package com.aukcje.controller.dictionaries;

import com.aukcje.exception.customException.CouldNotRateUser;
import com.aukcje.exception.customException.PurchaseNotFoundException;
import com.aukcje.model.UserRatingModel;
import com.aukcje.service.iface.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@PreAuthorize("hasAuthority('ROLE_USER')")
@RestController
@RequestMapping("/ocen-sprzedawce")
public class UserRatingRestController {

    private final UserRatingService userRatingService;

    @PostMapping()
    public void rateUser(HttpServletResponse response,
                         @RequestBody UserRatingModel userRatingModel ) throws CouldNotRateUser, PurchaseNotFoundException {
        userRatingService.addRatingForUser(userRatingModel);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}

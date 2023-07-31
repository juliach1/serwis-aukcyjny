package com.aukcje.service;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.dto.mapper.CartOfferDTOMapper;
import com.aukcje.entity.CartOffer;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.model.CartOfferModel;
import com.aukcje.repository.CartOfferRepository;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.CartOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartOfferServiceImpl implements CartOfferService {

    @Autowired
    CartOfferRepository cartOfferRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<CartOfferDTO> getAll(Long userId) {
        List<CartOffer> cartOffers = cartOfferRepository.findAllByUserId(userId);

        return createOfferDTO(cartOffers);
    }

    @Override
    public void add(Long userId, Long offerId, Integer quantity) {

        if(userId != null && offerId != null) {

            if (quantity == null) {
                quantity = 1;
            }
            CartOfferModel cartOfferModel = new CartOfferModel(offerId, userId, quantity);
            cartOfferRepository.save(getCart0fferFromCartOfferModel(cartOfferModel));
        }

    }

    private CartOffer getCart0fferFromCartOfferModel(CartOfferModel cartOfferModel){
        Offer offer = offerRepository.findById(cartOfferModel.getOfferId()).orElse(null);
        User user = userRepository.findByUsername("root");

        CartOffer cartOffer = new CartOffer();
        cartOffer.setQuantity(cartOfferModel.getQuantity());
        cartOffer.setUser(user);
        cartOffer.setOffer(offer);

        return cartOffer;

    }
    private CartOfferDTO createOfferDTO(CartOffer offer){
        return CartOfferDTOMapper.instance.cartOfferDTO(offer);
    }

    private List<CartOfferDTO> createOfferDTO(List<CartOffer> cartOffers){
        List<CartOfferDTO> cartOfferDTOS = new ArrayList<>();

        for (CartOffer cartOffer : cartOffers){
            cartOfferDTOS.add(createOfferDTO(cartOffer));
        }

        return cartOfferDTOS;
    }
}

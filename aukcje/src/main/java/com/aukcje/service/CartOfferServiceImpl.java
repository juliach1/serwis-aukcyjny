package com.aukcje.service;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.dto.mapper.CartOfferDTOMapper;
import com.aukcje.entity.CartOffer;
import com.aukcje.entity.Offer;
import com.aukcje.repository.CartOfferRepository;
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

    @Override
    public List<CartOfferDTO> getAll(Long userId) {
        List<CartOffer> cartOffers = cartOfferRepository.findAllByUserId(userId);

        return createOfferDTO(cartOffers);
    }

    @Override
    public void add(Long userId, Long offerId) {

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

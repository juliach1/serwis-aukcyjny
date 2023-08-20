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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public CartOfferDTO getOne(Long cartOfferId) {
        return createOfferDTO(cartOfferRepository.getOne(cartOfferId));
    }

    @Transactional
    @Override
    public void add(Long userId, Long offerId, Integer quantityToAdd) {

        if(userId != null && offerId != null) {

            if (quantityToAdd == null) {
                quantityToAdd = 1;
            }

            CartOffer existingCartOffer = cartOfferRepository.findByOfferIdAndUserId(offerId, userId);
            Offer offer = offerRepository.getOne(offerId);

            CartOffer offerToSave;

            if(existingCartOffer!=null){
                Integer currentQuantity = existingCartOffer.getQuantity();
                Integer offerQuantity = offer.getQuantity();
                Integer possibleQuantityToAdd = offerQuantity - currentQuantity;

                if(quantityToAdd<=possibleQuantityToAdd){
                    existingCartOffer.setQuantity(currentQuantity+quantityToAdd);
                }else {
                    existingCartOffer.setQuantity(currentQuantity+possibleQuantityToAdd);
                }
                offerToSave = existingCartOffer;

            }else {
                CartOfferModel cartOfferModel = new CartOfferModel(offerId, userId, quantityToAdd);
                offerToSave = getCart0fferFromCartOfferModel(cartOfferModel);
            }
            cartOfferRepository.save(offerToSave);
        }
    }

    @Override
    public void delete(Long cartOfferId) {
        cartOfferRepository.deleteById(cartOfferId);
    }

    @Override
    @Transactional
    public void changeQuantity(Long cartOfferId, Integer pcs) {
        CartOffer cartOffer = cartOfferRepository.getOne(cartOfferId);
        int newQuantity = cartOffer.getQuantity()-pcs;

        if(newQuantity <= 0){
            cartOfferRepository.deleteById(cartOfferId);
        }else{
            cartOffer.setQuantity(newQuantity);
            cartOfferRepository.save(cartOffer);
        }
    }

    @Override
    @Transactional
    public boolean isCartOfferAssignedToUser(Long userId, Long cartOfferId) {
        CartOfferDTO cartOfferDTO = getOne(cartOfferId);

        return Objects.equals(userId, cartOfferDTO.getUser().getId());
    }


    private CartOffer getCart0fferFromCartOfferModel(CartOfferModel cartOfferModel){
        Offer offer = offerRepository.findById(cartOfferModel.getOfferId()).orElse(null);

        User user = userRepository.findById(cartOfferModel.getUserId()).orElse(null);

        CartOffer cartOffer = new CartOffer();
        cartOffer.setQuantity(cartOfferModel.getQuantity());
        cartOffer.setUser(user);
        cartOffer.setOffer(offer);

        return cartOffer;
    }
    public CartOfferDTO createOfferDTO(CartOffer offer){
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

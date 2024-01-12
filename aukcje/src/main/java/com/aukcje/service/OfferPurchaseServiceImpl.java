package com.aukcje.service;

import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.dto.mapper.OfferPurchaseInfoDTOMapper;
import com.aukcje.entity.*;
import com.aukcje.enums.OfferStatusEnum;
import com.aukcje.enums.PurchaseStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.*;
import com.aukcje.model.OfferPurchaseModel;
import com.aukcje.repository.*;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OfferPurchaseServiceImpl implements OfferPurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PurchaseStatusRepository purchaseStatusRepository;

    @Autowired
    private OfferPurchaseInfoRepository offerPurchaseInfoRepository;

    @Autowired
    private OfferStatusRepository offerStatusRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private CartOfferService cartOfferService;

    @Override
    public void setRating(Long purchaseId, UserRating userRating) throws PurchaseNotFoundException {
        OfferPurchaseInfo offerPurchaseInfo = offerPurchaseInfoRepository.findById(purchaseId).orElseThrow(PurchaseNotFoundException::new);
        offerPurchaseInfo.setUserRating(userRating);
        System.out.println("ZAPISYWANIE USERRATING: "+userRating.getRating());
        offerPurchaseInfoRepository.save(offerPurchaseInfo);
    }

    @Override
    public void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) throws UserNotFoundException, AddressNotFoundException, OfferNotFoundException, OfferNotActiveException, PurchaseStatusNotFoundException, OfferStatusNotFoundException {
        System.out.println("-------> Metoda głowna w serwisie");

        List<OfferPurchaseInfo> offerPurchaseInfo = checkAndCreatePurchaseInfos(offerPurchaseModels, buyerId, addressId);
        save0fferPurchases(offerPurchaseInfo);
    }

    @Override
    public OfferPurchaseInfoDTO getById(Long id) {
        //todo: dodaj błąd: nie znaleziono transakcji
        return createOfferPurchaseInfoDTO(offerPurchaseInfoRepository.findById(id).orElse(null));
    }

    @Override
    public List<OfferPurchaseInfoDTO> getAllByBuyerId(Long userId) {
        List<OfferPurchaseInfo> offerPurchaseInfos = offerPurchaseInfoRepository.findOfferPurchaseInfoByBuyerIdOrderByPurchaseTimeDesc(userId);

        return createOfferPurchaseInfoDTO(offerPurchaseInfos);
    }

    @Override
    public List<OfferPurchaseInfoDTO> getAllBySellerId(Long userId) {
        List<OfferPurchaseInfo> offerPurchaseInfos = offerPurchaseInfoRepository.findOfferPurchaseInfoBySellerIdOrderByPurchaseTimeDesc(userId);

        return createOfferPurchaseInfoDTO(offerPurchaseInfos);
    }

    @Override
    @Transactional
    public void updatePurchaseStatus(Long offerPurchaseId, Integer purchaseStatusId) {
        PurchaseStatus purchaseStatus = purchaseStatusRepository.getOne(purchaseStatusId);
        OfferPurchaseInfo offerPurchaseInfo = offerPurchaseInfoRepository.getOne(offerPurchaseId);

        offerPurchaseInfo.setPurchaseStatus(purchaseStatus);

        offerPurchaseInfoRepository.save(offerPurchaseInfo);
    }

    private List<OfferPurchaseInfoDTO> createOfferPurchaseInfoDTO(List<OfferPurchaseInfo> offerPurchaseInfos){
        List<OfferPurchaseInfoDTO> purchaseInfoDTOS = new ArrayList<>();

        for (OfferPurchaseInfo purchaseInfo : offerPurchaseInfos){
            OfferPurchaseInfoDTO offerPurchaseInfoDTO = createOfferPurchaseInfoDTO(purchaseInfo);
            offerPurchaseInfoDTO.setPurchaseTime(purchaseInfo.getPurchaseTime());
            System.out.println("ZAKUPY UŻYTKOWNIKA: ");
            System.out.println("oferta: " + offerPurchaseInfoDTO.getOffer().getOfferDetails().getTitle());
            System.out.println("liczba sztuk: " + offerPurchaseInfoDTO.getQuantity());
            System.out.println("buyer: " + offerPurchaseInfoDTO.getBuyer().getUsername());
            System.out.println("seller: " + offerPurchaseInfoDTO.getSeller().getUsername());
            System.out.println("data zakupu: " + offerPurchaseInfoDTO.getPurchaseTime().toString());
            System.out.println("adres: " + offerPurchaseInfoDTO.getAddress().getStreetName());
            System.out.println("--------------------------------------------");

            purchaseInfoDTOS.add(offerPurchaseInfoDTO);
        }

        return purchaseInfoDTOS;
    }

    private OfferPurchaseInfoDTO createOfferPurchaseInfoDTO(OfferPurchaseInfo offerPurchaseInfo){
        return OfferPurchaseInfoDTOMapper.instance.offerPurchaseInfoDTO(offerPurchaseInfo);
    }

    private void save0fferPurchases(List<OfferPurchaseInfo> offerPurchaseInfos){
        for (OfferPurchaseInfo offerPurchaseInfo : offerPurchaseInfos){

            Offer offer = offerPurchaseInfo.getOffer();
            cartOfferService.deleteAllByOfferId(offer.getId());
            offerRepository.save(offerPurchaseInfo.getOffer());
            offerPurchaseInfoRepository.save(offerPurchaseInfo);
            System.out.println("zapisano oferte: "+offerPurchaseInfo.getOffer().getOfferDetails().getTitle());
        }
    }

    private List<OfferPurchaseInfo> checkAndCreatePurchaseInfos(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) throws UserNotFoundException, OfferNotFoundException, AddressNotFoundException, PurchaseStatusNotFoundException, OfferStatusNotFoundException {
        System.out.println("-------> Metoda prywatna w serwisie");
        System.out.println("-------> OFFERPURCHASEMODEL - rozmiar: "+offerPurchaseModels.size());

        List<OfferPurchaseInfo> offerPurchaseInfos = new ArrayList<>();
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("Nie znaleziono konta sprzedawcy"));
        Address address = addressRepository.findById(addressId).orElseThrow( AddressNotFoundException::new );
        PurchaseStatusEnum statusNew = PurchaseStatusEnum.NEW;
        PurchaseStatus purchaseStatus = purchaseStatusRepository.findById( statusNew.getId() ).orElseThrow(() -> new PurchaseStatusNotFoundException( statusNew.toString() ));

        for(OfferPurchaseModel offerPurchaseModel: offerPurchaseModels){
            Offer offer = offerRepository.findById(offerPurchaseModel.getOfferId()).orElse(null);
            if(offer == null || !Objects.equals(offer.getOfferStatus().getId(), OfferStatusEnum.ACTIVE.getId())){
                throw new OfferNotFoundException();
            }else if(offer.getUser() == null) {
                throw new UserNotFoundException("Nie znaleziono konta kupującego");
            }
            offer.setOfferStatus(offerStatusRepository.findById(OfferStatusEnum.ENDED.getId()).orElseThrow(() -> new OfferStatusNotFoundException( OfferStatusEnum.ENDED.name() )));
            User seller = offer.getUser();

            OfferPurchaseInfo offerPurchaseInfo = OfferPurchaseInfo.builder()
                    .purchaseTime(LocalDateTime.now())
                    .address(address)
                    .seller(seller)
                    .buyer(buyer)
                    .offer(offer)
                    .quantity(offerPurchaseModel.getQuantity())
                    .price(offerPurchaseModel.getPrice())
                    .purchaseStatus(purchaseStatus)
                    .build();

            System.out.println("--------------Tworzenie OPI--------------------");
            System.out.println(offerPurchaseInfo.getPurchaseTime());
            System.out.println(offerPurchaseInfo.getAddress().getCity());
            System.out.println(offerPurchaseInfo.getSeller()+seller.getUsername());
            System.out.println(offerPurchaseInfo.getBuyer()+seller.getUsername());
            System.out.println("offer: " + offerPurchaseInfo.getOffer().getId());
            System.out.println("quantity: " + offerPurchaseInfo.getQuantity());
            System.out.println("price: " + offerPurchaseInfo.getPrice());
            System.out.println("purchaseStatus: " + offerPurchaseInfo.getPurchaseStatus());

            offerPurchaseInfos.add(offerPurchaseInfo);
        }
        return offerPurchaseInfos;
    }
}

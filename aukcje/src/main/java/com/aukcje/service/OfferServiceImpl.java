package com.aukcje.service;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferPhotoDTO;
import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.dto.mapper.OfferDTOMapper;
import com.aukcje.entity.*;
import com.aukcje.enums.OfferStatusEnum;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.model.mapper.OfferMapper;
import com.aukcje.repository.*;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.OfferPhotoService;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserFavoriteOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class OfferServiceImpl implements OfferService {

    private final ServletContext servletContext;
    private final OfferRepository offerRepository;
    private final UserFavoriteOfferService userFavoriteOfferService;
    private final CartOfferService cartOfferService;
    private final OfferStatusRepository offerStatusRepository;
    private final OfferPhotoService offerPhotoService;
    private final OfferTypeRepository offerTypeRepository;
    private final CustomOfferRepository customOfferRepository;
    private final ItemConditionRepository itemConditionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OfferPhotoRepository offerPhotoRepository;

    private final Integer DEFAULT_PAGE_SIZE = 12;
    @Value("${files.path}")
    private String filesPath;


    @Override
    public OfferDTO findById(Long id) throws OfferNotFoundException, OfferStatusNotFoundException {
        Offer offer = offerRepository.findById(id).orElseThrow(OfferNotFoundException::new);
        return createOfferDTO(offer);
    }

    private Offer checkAndSetStatus(Offer offer) throws OfferStatusNotFoundException {
        if(hasEnded(offer)){
            offer = setStatusEnded(offer);
        }
        return offer;
    }

    private boolean hasEnded(Offer offer){
        return offer.getEndDate().isAfter(LocalDateTime.now());
    }

    private Offer setStatusEnded(Offer offer) throws OfferStatusNotFoundException {
        OfferStatus offerStatus = offerStatusRepository.findById(OfferStatusEnum.ENDED.getId()).orElseThrow(OfferStatusNotFoundException::new);
        offer.setOfferStatus(offerStatus);
        return offerRepository.save(offer);
    }
    @Override
    public List<OfferDTO> findNewActiveAuctions(Integer pageSize) throws OfferStatusNotFoundException {
        return findNewActiveByOfferTypeId(OfferTypeEnum.AUCTION.getId(), pageSize);
    }

    @Override
    public List<OfferDTO> findNewActiveBuyNow(Integer pageSize) throws OfferStatusNotFoundException {
        return findNewActiveByOfferTypeId(OfferTypeEnum.BUY_NOW.getId(), pageSize);
    }

    @Override
    public List<OfferDTO> findActiveByUserId(long id, Integer pageSize) throws OfferStatusNotFoundException {
        List<Offer> offers = offerRepository.findByUserIdAndOfferStatusIdOrderByInsertDateDesc(id, OfferStatusEnum.ACTIVE.getId(), setPageSize(pageSize)).toList();

        return createOfferDTO(offers);
    }

    @Override
    public List<OfferDTO> findActiveAuctionsByUserId(Long userId, Integer pageSize) throws OfferStatusNotFoundException {
        List<Offer> offers = offerRepository.findByUserIdAndOfferTypeIdAndOfferStatusIdOrderByInsertDateDesc(userId, OfferTypeEnum.AUCTION.getId(), OfferStatusEnum.ACTIVE.getId(), setPageSize(pageSize)).toList();
        return createOfferDTO(offers);
    }

    @Override
    public List<OfferDTO> findActiveBuyNowByUserId(Long userId, Integer pageSize) throws OfferStatusNotFoundException {
        List<Offer> offers = offerRepository.findByUserIdAndOfferTypeIdAndOfferStatusIdOrderByInsertDateDesc(userId, OfferTypeEnum.BUY_NOW.getId(), OfferStatusEnum.ACTIVE.getId(), setPageSize(pageSize)).toList();
        return createOfferDTO(offers);
    }

    @Override
    public List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel) throws OfferStatusNotFoundException {
        return createOfferDTO(customOfferRepository.findByOfferSearchModel(offerSearchModel).toList());
    }

    @Override
    public Boolean isOfferTypeAuction(OfferDTO offer) {
        return OfferTypeEnum.AUCTION.toString().equals(offer.offerType.getName());
    }

    @Override
    public Boolean isOfferAssignedToUser(Long userId, Long offerId) {
        List<Offer> offers = offerRepository.findByUserId(userId);
        Optional<Offer> offer = offerRepository.findById(offerId);

        if(offer.isPresent()){
            for(Offer tempOffer : offers){
                if(tempOffer.getId().equals(offerId)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<OfferDTO> findActiveFavoriteForUser(Long userId, Integer pageSize) {
        List<UserFavoriteOfferDTO> userFavoriteOfferDTOS = userFavoriteOfferService.getActiveByUserId(userId, pageSize);
        return getFromUserFavoriteOfferDTOS(userFavoriteOfferDTOS);
    }

    @Override
    public Long save(OfferAddModel offerModel, Long userId, MultipartFile multipartFile) {
        Category category = categoryRepository.findById(offerModel.getCategoryId()).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        OfferStatus offerStatus = offerStatusRepository.findById(1).orElse(null);
        OfferType offerType = offerTypeRepository.findByName(offerModel.getOfferType());
        OfferDetails offerDetails = getOfferDetails(offerModel);

        Offer offer =  OfferMapper.offer(offerModel, offerDetails, category, user, offerStatus, offerType);
        offerRepository.save(offer);

        if(Objects.nonNull(multipartFile)){
            addPhoto(offer.getId(), multipartFile);
        }
        return offer.getId();
    }

    private OfferDetails getOfferDetails(OfferAddModel offerModel) {
        ItemCondition itemCondition = itemConditionRepository.findByName(offerModel.getItemCondition());
        OfferDetails offerDetails = new OfferDetails();

        offerDetails.setTitle(offerModel.getTitle());
        offerDetails.setDescription(offerModel.getDescription());
        offerDetails.setItemCondition(itemCondition);
        return offerDetails;
    }

    @Override
    public void update(OfferAddModel offerModel, Long userId, MultipartFile multipartFile) {
        save(offerModel, userId, multipartFile);
    }

    @Override
    @Transactional
    public void delete(Long offerId) {
        offerPhotoService.deleteByOfferId(offerId);
        cartOfferService.deleteAllByOfferId(offerId);
        userFavoriteOfferService.deleteAllByOfferId(offerId);
        offerRepository.deleteById(offerId);
    }

    @Override
    public Integer getActiveOffersNumberByUserId(Long userId) {
        return offerRepository.countOfferByUserIdAndOfferStatusId(userId, OfferStatusEnum.ACTIVE.getId());
    }

    private List<OfferDTO> getFromUserFavoriteOfferDTOS(List<UserFavoriteOfferDTO> userFavoriteOfferDTOS) {
        List<OfferDTO> offers = new ArrayList<>();
        for(UserFavoriteOfferDTO userFavoriteOffer : userFavoriteOfferDTOS){
            offers.add(userFavoriteOffer.getOffer());
        }
        return offers;
    }

    private void addPhoto(long offerId, MultipartFile multipartFile) {

        // exit if filesize < 1
        if(!(multipartFile.getSize() > 1)){
            return;
        }

        Offer offer = offerRepository.findById(offerId).orElse(null);
        if(Objects.isNull(offer)){
            return;
        }

        Long photoNr = 1L;
        if(Objects.nonNull(offerPhotoService.findByOfferId(offerId)) && offerPhotoService.findByOfferId(offerId).size()>0) {
                photoNr = Long.parseLong(offerPhotoService.findByOfferId(offerId).get(offerPhotoService.findByOfferId(offerId).size()-1).getPath())+1;
        }

        String path;
        String pathTarget;
        if(photoNr.equals(1L)){
            path = createFolderForOffer(offerPath(), offerId) + "\\" + photoNr + ".png";
            pathTarget = createFolderForOffer(offerPathTarget(), offerId) + "\\" + photoNr + ".png";
        }else {
            // prepare paths
            path = offerPath() + "\\" + offerId + "\\" + photoNr + ".png";
            pathTarget = offerPathTarget() + "\\" + offerId + "\\" + photoNr + ".png";
        }

        File file = new File(path);
        File fileTarget = new File(pathTarget);

        saveOfferPhoto(file, multipartFile);
        saveOfferPhoto(fileTarget, multipartFile);

        OfferPhoto offerPhoto = new OfferPhoto();
        offerPhoto.setOffer(offer);
        offerPhoto.setPath(photoNr.toString());
        offerPhoto.setSequence(offerNextPhotoOrder(offer));

        offerPhotoRepository.save(offerPhoto);
    }

    private Integer offerNextPhotoOrder(Offer nieruchomosc) {
        int next = 1;

        if(offerPhotoService.findByOfferId(nieruchomosc.getId()).isEmpty()) return next;

        for(OfferPhotoDTO photo : offerPhotoService.findByOfferId(nieruchomosc.getId())){
            if(photo.getSequence() > next){
                next = photo.getSequence();
            }
        }
        return next + 1;
    }

    private void saveOfferPhoto(File file, MultipartFile multipartFile) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            byte[] bytes = multipartFile.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("OfferServiceImpl.saveOfferPhoto FileNotFoundException: Błąd podczas tworzenia pliku");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("OfferServiceImpl.saveOfferPhoto IOException");
            e.printStackTrace();
        }
    }

    private String offerPath(){
        return filesPath+"\\img\\offers";
    }

    private String offerPathTarget(){
        return servletContext.getRealPath("/WEB-INF/files/img/offers");
    }

    private String createFolderForOffer(String path, long offerId) {
        String createdPath = path +"\\"+ offerId;
        File folder = new File(createdPath);
        folder.mkdirs();

        return createdPath;
    }

    private List<OfferDTO> findNewActiveByOfferTypeId(Integer offerTypeId, Integer pageSize) throws OfferStatusNotFoundException {
        if(isInvalid(pageSize)) pageSize = DEFAULT_PAGE_SIZE;

        List<Offer> offers = offerRepository.findByOfferTypeIdAndOfferStatusIdOrderByInsertDateDesc( offerTypeId, OfferStatusEnum.ACTIVE.getId(), setPageSize(pageSize) ).toList() ;

        List<OfferDTO> offerDTOS = createOfferDTO( offers );

        return offerDTOS;
    }

    private Boolean isInvalid(Integer pageSize){
        return pageSize == null || pageSize<=0;
    }

    private Pageable setPageSize(Integer size){
        return PageRequest.of(0, size);
    }

    public void setIsFavorite(List<OfferDTO> offerDTOS, Long userId) {
        for (OfferDTO offerDTO : offerDTOS){
            UserFavoriteOfferDTO offer = userFavoriteOfferService.geByUserIdAndOfferId(userId, offerDTO.getId());
            offerDTO.setIsFavorite(offer != null);
        }
    }

    @Override
    public boolean isOfferActive(Offer offer) {
        return offer.getOfferStatus().getId().equals(OfferStatusEnum.ACTIVE.getId());
    }

    @Override
    public boolean isOfferActive(OfferDTO offerDTO) {
        String offerStatus = offerDTO.getOfferStatus().getName();
        String activeStatus = OfferStatusEnum.ACTIVE.toString();

        return Objects.equals( offerStatus, activeStatus );
    }

    private OfferDTO createOfferDTO(Offer offer) {
        OfferDTO offerDTO = OfferDTOMapper.instance.offerDTO(offer);
        List<OfferPhotoDTO> offerPhotoDTOS = offerPhotoService.findByOfferId(offerDTO.getId());
        offerDTO.setOfferPhoto(offerPhotoDTOS);
        return offerDTO;
    }

    private List<OfferDTO> createOfferDTO(List<Offer> offers) throws OfferStatusNotFoundException {
        List<OfferDTO> offerDTOS = new ArrayList<>();

        for(Offer offer : offers){
            checkAndSetStatus(offer);
            offerDTOS.add( createOfferDTO(offer) );
        }
        return offerDTOS;
    }
}

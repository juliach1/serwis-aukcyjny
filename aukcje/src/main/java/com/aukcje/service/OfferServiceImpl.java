package com.aukcje.service;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferPhotoDTO;
import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.dto.mapper.OfferDTOMapper;
import com.aukcje.entity.*;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.model.mapper.OfferMapper;
import com.aukcje.repository.*;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.OfferPhotoService;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserFavoriteOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CartOfferService cartOfferService;

    @Autowired
    private UserFavoriteOfferService userFavoriteOfferService;

    @Autowired
    private OfferStatusRepository offerStatusRepository;

    @Autowired
    private OfferPhotoService offerPhotoService;

    @Autowired
    private OfferTypeRepository offerTypeRepository;

    @Autowired
    private CustomOfferRepository customOfferRepository;

    @Autowired
    private ItemConditionRepository itemConditionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferPhotoRepository offerPhotoRepository;

    private final Integer DEFAULT_PAGE_SIZE = 12;
    @Value("${files.path}")
    private String filesPath;

    @Autowired
    private ServletContext servletContext;

    @Override
    public OfferDTO findById(Long id) {
        OfferDTO offerDTO = createOfferDTO( offerRepository.findById(id).orElse(new Offer()) );
        offerDTO.setOfferPhoto( offerPhotoService.findByOfferId(id) );
        return offerDTO;
    }

    @Override
    public List<OfferDTO> findByUserId(Long userId) {
        List<Offer> offers = offerRepository.findByUserId(userId);
        return createOfferDTO(offers);
    }


    @Override
    public List<OfferDTO> findNewAuctions(Integer pageSize) {
        return findNewByOfferTypeId(2, pageSize);
    }

    @Override
    public List<OfferDTO> findNewBuyNow(Integer pageSize) {
        return findNewByOfferTypeId(1, pageSize);
    }

    @Override
    public List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel) {
        return createOfferDTO(customOfferRepository.findByOfferSearchModel(offerSearchModel).toList());
    }

    @Override
    public Boolean isOfferTypeAuction(OfferDTO offer) {
        return OfferTypeEnum.AUKCJA.toString().equals(offer.offerType.getName());
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

    private OfferDetails getOfferDetails(OfferAddModel offerModel){
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
        userFavoriteOfferService.deleteByOfferId(offerId);
        offerRepository.deleteById(offerId);
    }

    private void addPhoto(long offerId, MultipartFile multipartFile){

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

    private Integer offerNextPhotoOrder(Offer nieruchomosc){
        int next = 1;

        if(offerPhotoService.findByOfferId(nieruchomosc.getId()).isEmpty()) return next;

        for(OfferPhotoDTO photo : offerPhotoService.findByOfferId(nieruchomosc.getId())){
            if(photo.getSequence() > next){
                next = photo.getSequence();
            }
        }
        return next + 1;
    }

    private void saveOfferPhoto(File file, MultipartFile multipartFile){
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

    private String createFolderForOffer(String path, long offerId){
        String createdPath = path +"\\"+ offerId;
        File folder = new File(createdPath);
        folder.mkdirs();

        return createdPath;
    }

    private List<OfferDTO> findNewByOfferTypeId(Integer offerTypeId, Integer pageSize) {
        if(isInvalid(pageSize)) pageSize = DEFAULT_PAGE_SIZE;

        List<Offer> offers = offerRepository.findByOfferTypeIdOrderByInsertDateDesc( offerTypeId, setPageSize(pageSize) ).toList() ;
        List<OfferDTO> offerDTOS = createOfferDTO( offers );


        return offerDTOS;
    }

    private Boolean isInvalid(Integer pageSize){
        return pageSize == null || pageSize<=0;
    }

    private Pageable setPageSize(Integer size){
        return PageRequest.of(0, size);
    }

    public void setIsFavorite(List<OfferDTO> offerDTOS, Long userId){
        for (OfferDTO offerDTO : offerDTOS){
            UserFavoriteOfferDTO offer = userFavoriteOfferService.geByUserIdAndOfferId(userId, offerDTO.getId());
            offerDTO.setIsFavorite(offer != null);
        }
    }

    private OfferDTO createOfferDTO(Offer offer){
        return OfferDTOMapper.instance.offerDTO(offer);
    }

    private List<OfferDTO> createOfferDTO(List<Offer> offers){
        List<OfferDTO> offerDTOS = new ArrayList<>();

        for(Offer offer : offers){
            offerDTOS.add( createOfferDTO(offer) );
        }
        return offerDTOS;
    }
}

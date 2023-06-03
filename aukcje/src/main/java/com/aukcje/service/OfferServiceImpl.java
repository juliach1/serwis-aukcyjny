package com.aukcje.service;

import com.aukcje.dto.CategoryPathCategoryDTO;
import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferPhotoDTO;
import com.aukcje.dto.mapper.CategoryDTOMapper;
import com.aukcje.dto.mapper.OfferDTOMapper;
import com.aukcje.entity.*;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.model.mapper.OfferMapper;
import com.aukcje.repository.*;
import com.aukcje.service.iface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferStatusRepository offerStatusRepository;
    @Autowired
    private OfferPhotoService offerPhotoService;

    @Autowired
    private OfferTypeRepository offerTypeRepository;


    @Autowired
    private CategoryService categoryService;

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

        offerDTO.setOfferPhotoDTO( offerPhotoService.findByOfferId(id) );

        return offerDTO;
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

        if(offer.isEmpty()) return false;

        for(Offer tempOffer : offers){
            if(tempOffer.getId().equals(offerId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Long save(OfferAddModel offerModel, Long userId, MultipartFile multipartFile) {
        ItemCondition itemCondition = itemConditionRepository.findByName(offerModel.getItemCondition());
        Category category = categoryRepository.findById(offerModel.getCategoryId()).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        OfferStatus offerStatus = offerStatusRepository.findById(1).orElse(null);
        OfferType offerType = offerTypeRepository.findByName(offerModel.getOfferType());

        OfferDetails offerDetails = new OfferDetails();
        offerDetails.setTitle(offerModel.getTitle());
        offerDetails.setDescription(offerModel.getDescription());
        offerDetails.setItemCondition(itemCondition);

        Offer offer = new Offer();
        offer.setCategory(category);
        offer.setUser(user);
        offer.setInsertDate(LocalDateTime.now());
        offer.setViews(0l);
        offer.setOfferStatus(offerStatus);
        offer.setOfferType(offerType);
        offer.setOfferDetails(offerDetails);
        offer.setQuantity(offerModel.getQuantity());
        offer.setPrice(Double.valueOf(offerModel.getPrice()));

        System.out.println("-----------DODAWANY OBIEKT-----------");

        System.out.println(offer.getOfferDetails().getTitle());
        System.out.println(offer.getOfferDetails().getDescription());
        System.out.println("stan: " + offer.getOfferDetails().getItemCondition());

        System.out.println("kategoria: " + offer.getCategory());
        System.out.println("użytkownik: " + offer.getUser());
        System.out.println("kategoria: " + offer.getInsertDate());
        System.out.println("status: " + offer.getOfferStatus());
        System.out.println("typ: " + offer.getOfferType());

        offerRepository.save(offer);

        if(Objects.nonNull(multipartFile)){
            addPhoto(offer.getId(), multipartFile);
        }

        return offer.getId();
    }

    @Override
    public void update(OfferAddModel offerModel, Long userId, MultipartFile multipartFile) {
//        Offer offer = OfferMapper.offer(offerModel, userId);

        save(offerModel, userId, multipartFile);
    }





    private void addPhoto(long offerId, MultipartFile multipartFile){
        System.out.println("File received: !");
        System.out.println("FileName: " +multipartFile.getName());
        System.out.println("FileSize: " +multipartFile.getSize());

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
        if(offerPhotoService.findByOfferId(nieruchomosc.getId()).isEmpty()){
            return next;
        }

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

        List<Offer> offers = offerRepository.findNewByOfferTypeId( offerTypeId, setPageSize(pageSize) ).toList() ;
        List<OfferDTO> offerDTOS = createOfferDTO( offers );


        return offerDTOS;
    }

    private Boolean isInvalid(Integer pageSize){
        return pageSize == null || pageSize<=0;
    }
    private Pageable setPageSize(Integer size){
        return PageRequest.of(0, size);
    }


    private OfferDTO createOfferDTO(Offer offer){
        OfferDTO offerDTO = OfferDTOMapper.instance.offerDTO(offer);
        Category category = offer.getCategory();
        System.out.println("-----------KATEGORIA: "+category.getName());

        List<CategoryPathCategoryDTO> categoryPathCategoryDTOS = categoryService.getCategoryPath(category);

        offerDTO.setCategoryPath(categoryPathCategoryDTOS);

        //TODO: SPRAWDZIĆ, DLACZEGO CATEGORYDTO NIE DODAJE SIĘ W OFFERDTOMAPPER!
        offerDTO.setCategoryDTO(CategoryDTOMapper.instance.categoryDTO(category));

        return offerDTO ;
    }

    private List<OfferDTO> createOfferDTO(List<Offer> offers){
        List<OfferDTO> offerDTOS = new ArrayList<>();

        for(Offer offer : offers){
            offerDTOS.add( createOfferDTO(offer) );
        }
        return offerDTOS;
    }


}

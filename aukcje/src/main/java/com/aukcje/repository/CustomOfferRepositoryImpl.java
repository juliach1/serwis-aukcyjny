package com.aukcje.repository;

import com.aukcje.entity.Offer;
import com.aukcje.entity.OfferType;
import com.aukcje.enums.OfferStatusEnum;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomOfferRepositoryImpl implements CustomOfferRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Offer> criteriaQuery;
    private Root<Offer> root;

    private final Integer DEFAULT_PAGE_SIZE = 12;

//TODO WYCZYŚCIĆ NIEPOTRZEBNE

    @Override
    public Page<Offer> findByOfferSearchModel(OfferSearchModel offerSearchModel) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Offer.class);
        root = criteriaQuery.from(Offer.class);

        Predicate offerStatusPredicate = createOfferStatusPredicate(offerSearchModel);
        List<Predicate> offerTypePredicates = createOfferTypePredicates(offerSearchModel);
        List<Predicate> phrasePredicates = createPhrasePredicates(offerSearchModel);
        List<Predicate> categoryPredicates = createCategoryPredicates(offerSearchModel);
        List<Predicate> pricePredicates = createPricePredicates(offerSearchModel);

        List<Predicate> allPredicates = new ArrayList<>();

        allPredicates.add(criteriaBuilder.and(offerStatusPredicate));

        if (!offerTypePredicates.isEmpty()){
            allPredicates.add(criteriaBuilder.or(offerTypePredicates.toArray(new Predicate[offerTypePredicates.size()])));
        }
        if(!phrasePredicates.isEmpty()){
            allPredicates.add(criteriaBuilder.or(phrasePredicates.toArray(new Predicate[phrasePredicates.size()])));
        }
        if(!categoryPredicates.isEmpty()){
            allPredicates.add(criteriaBuilder.and(categoryPredicates.toArray(new Predicate[categoryPredicates.size()])));
        }
        if(!pricePredicates.isEmpty()){
            allPredicates.add(criteriaBuilder.and(pricePredicates.toArray(new Predicate[pricePredicates.size()])));
        }

        if (!isNumericValueSet(offerSearchModel.getPageSize()))
            offerSearchModel.setPageSize(DEFAULT_PAGE_SIZE);


        if (allPredicates.isEmpty()) {
            criteriaQuery.select(root);

            return Utils.listToPageMapper(entityManager.createQuery(criteriaQuery).getResultList(),
                    PageRequest.of(0, offerSearchModel.getPageSize()));
        }

        criteriaQuery.select(root)
                .where(allPredicates.toArray(new Predicate[allPredicates.size()]));

        Page<Offer> offers = Utils.listToPageMapper(entityManager.createQuery(criteriaQuery).getResultList(),
                PageRequest.of(0, offerSearchModel.getPageSize()));

        return offers;
    }



    private List<Predicate> createAndPredicates(List<Predicate> ... predicateLists){
        List<Predicate> allPredicates = new ArrayList<>();

        for (List<Predicate> predicateList : predicateLists) {
            if(!predicateList.isEmpty()){
                allPredicates.add(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            }
        }
        return allPredicates;
    }

    private Predicate createOfferStatusPredicate(OfferSearchModel offerSearchModel) {
         Path<String> offerStatusPath = root.get("offerStatus").get("name");

         return criteriaBuilder.equal(offerStatusPath, OfferStatusEnum.ACTIVE.toString());
    }
    private List<Predicate> createPhrasePredicates(OfferSearchModel offerSearchModel) {

        List<Predicate> phrasePredicates = new ArrayList<>();
        String phrase = offerSearchModel.getPhrase();
        if (isTextValueSet(phrase)) {
            Path<String> descriptionPath = root.get("offerDetails").get("description");
            Path<String> titlePath =root.get("offerDetails").get("title");

            phrasePredicates.add(criteriaBuilder.like(titlePath, "%" + phrase + "%"));

            if (offerSearchModel.getSearchInDescription()) {
                phrasePredicates.add(criteriaBuilder.like(descriptionPath, "%" + phrase + "%"));
            }
        }
        return phrasePredicates;
    }

    private List<Predicate> createOfferTypePredicates(OfferSearchModel offerSearchModel) {
        List<Predicate> offerTypePredicates = new ArrayList<>();
        Path<OfferType> typePath = root.get("offerType").get("name");
        if (offerSearchModel.getOfferTypes() == null || offerSearchModel.getOfferTypes().contains(OfferTypeEnum.BUY_NOW.toString()) || offerSearchModel.getOfferTypes().isEmpty()) {
            offerTypePredicates.add(criteriaBuilder.equal(typePath, OfferTypeEnum.BUY_NOW.toString()));
        }
        if (offerSearchModel.getOfferTypes() == null ||offerSearchModel.getOfferTypes().contains(OfferTypeEnum.AUCTION.toString()) || offerSearchModel.getOfferTypes().isEmpty()) {
            offerTypePredicates.add(criteriaBuilder.equal(typePath, OfferTypeEnum.AUCTION.toString()));
        }

        return offerTypePredicates;
    }

    private List<Predicate> createCategoryPredicates(OfferSearchModel offerSearchModel){
        List<Predicate> categoryPredicates = new ArrayList<>();
        Path<Integer> categoryPath = root.get("category").get("id");
        Integer categoryId = offerSearchModel.getSearchCategory();
        if (categoryId != 0) {
            categoryPredicates.add(criteriaBuilder.equal(categoryPath, categoryId));
        }

        return categoryPredicates;
    }

    private List<Predicate> createPricePredicates(OfferSearchModel offerSearchModel){
        Path<Double> pricePath = root.get("price");
        List<Predicate> pricePredicates = new ArrayList<>();
        Double minPrice = offerSearchModel.getMinPrice();
        if (isNumericValueSet(minPrice)) {
            Predicate minPriceCriteria = criteriaBuilder.greaterThanOrEqualTo(pricePath, minPrice);
            pricePredicates.add(minPriceCriteria);
        }
        Double maxPrice = offerSearchModel.getMaxPrice();
        if (isNumericValueSet(maxPrice)) {
            Predicate maxPriceCriteria = criteriaBuilder.lessThanOrEqualTo(pricePath, maxPrice);
            pricePredicates.add(maxPriceCriteria);
        }
        System.out.println("MINPRICE "+minPrice+", MAXPRICE "+maxPrice);
        return pricePredicates;
    }

    private boolean isNumericValueSet(Number price) {
        return !(price == null || price.equals(0));
    }

    private boolean isTextValueSet(String text) {
        return !(text.isEmpty() || text.isBlank());
    }
}


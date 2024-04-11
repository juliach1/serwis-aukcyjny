package com.aukcje.repository;

import com.aukcje.entity.User;
import com.aukcje.entity.UserStatus;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.model.UserSearchModel;
import com.aukcje.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserStatusRepository userStatusRepository;


    @Override
    public Page<User> findByUserSearchModel(UserSearchModel userSearchModel) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();


        List<Predicate> statusPredicates = new ArrayList<>();
        Path<String> statusPath = root.get("userStatus").get("name");
        if(userSearchModel.getIsActive() != null && userSearchModel.getIsActive()){
            statusPredicates.add(criteriaBuilder.like( statusPath, UserStatusEnum.ACTIVE.toString() ));
        }
        if(userSearchModel.getIsBlocked() != null && userSearchModel.getIsBlocked()){
            statusPredicates.add(criteriaBuilder.like( statusPath, UserStatusEnum.BANNED.toString() ));
        }
        if(userSearchModel.getIsDeleted() != null && userSearchModel.getIsDeleted()){
            statusPredicates.add( criteriaBuilder.like( statusPath, UserStatusEnum.DELETED.toString() ));
        }

        List<Predicate> phrasePredicates = new ArrayList<>();
        if( !( userSearchModel.getPhrase() == null || userSearchModel.getPhrase().isBlank() || userSearchModel.getPhrase().isEmpty() )){
            String phrase = userSearchModel.getPhrase();
            Path<Long> idPath = root.get("id");
            Path<String> usernamePath = root.get("username");

            if( !Utils.isLong(userSearchModel.getPhrase()) ){
                phrasePredicates.add(criteriaBuilder.like( usernamePath, "%"+phrase+"%" ));
            }else {
//                phrasePredicates.add(criteriaBuilder.equal( idPath, Long.parseLong(phrase) ));
                phrasePredicates.add(criteriaBuilder.like( usernamePath, "%"+phrase+"%" ));
            }
        }


        if(userSearchModel.getPageSize() == null || userSearchModel.getPageSize() <=0) userSearchModel.setPageSize(10);

        if(!statusPredicates.isEmpty()){
            System.out.println("STATUS - NIE PUSTE");
            predicates.add(criteriaBuilder
                    .or(statusPredicates.toArray(new Predicate[statusPredicates.size()])));
        }
        if(!phrasePredicates.isEmpty()){
            System.out.println("phrase - NIE PUSTE");

            predicates.add(criteriaBuilder
                    .or(phrasePredicates.toArray(new Predicate[phrasePredicates.size()])));
        }

        if(predicates.isEmpty()){
            criteriaQuery.select(root);

            return Utils.listToPageMapper(entityManager.createQuery(criteriaQuery).getResultList(),
                    PageRequest.of(0, userSearchModel.getPageSize()));
        }

        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        Page<User> users = Utils.listToPageMapper(entityManager.createQuery(criteriaQuery).getResultList(),
                PageRequest.of(0, userSearchModel.getPageSize()));
        return users;
    }
}

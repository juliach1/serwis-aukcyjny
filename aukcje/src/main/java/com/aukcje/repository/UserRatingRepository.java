package com.aukcje.repository;

import com.aukcje.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    List<UserRating> getAllByUserId(Long userId);

    Long countByUserId(Long userId);

}

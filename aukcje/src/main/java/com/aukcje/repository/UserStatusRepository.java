package com.aukcje.repository;

import com.aukcje.entity.User;
import com.aukcje.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

    @Query("Select us from UserStatus us where us.id = ?1")
    UserStatus searchById(Integer id);

}

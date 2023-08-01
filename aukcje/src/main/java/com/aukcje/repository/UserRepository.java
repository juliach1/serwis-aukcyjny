package com.aukcje.repository;

import com.aukcje.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    Optional<User> findById(Long id);

    @Query("Select u from User u where u.username like %?1% order by u.id")
    Page<User> search(String searchPhrase, Pageable pageable);

    @Query("Select u from User u where u.username like %?1% order by u.id")
    ArrayList<User> search(String searchPhrase);

    @Query("Select u from User u where u.id = ?1 or u.username like %?2% order by u.id")
    Page<User> search(Long numericPhrase, String searchPhrase, Pageable pageable);

    @Query("Select u from User u where u.id = ?1 or u.username like %?2% order by u.id")
    ArrayList<User> search(Long numericPhrase, String searchPhrase);

    @Modifying
    @Query("Update User u set u.userStatus = (Select us from UserStatus us where us.id = ?1) where u.id = ?2")
    int setUserStatus(Integer statusNr, Long id);

}

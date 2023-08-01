package com.aukcje.repository;

import com.aukcje.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("Select a from Address a where a.user.id = ?1")
    List<Address> findByUserId(Long userId);
}

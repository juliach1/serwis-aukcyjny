package com.aukcje.repository;

import com.aukcje.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserIdAndIsDeletedIsFalse(Long userId);

    List<Address> findByUserId(Long userId);

    Optional<Address> findByIdAndIsDeletedIsFalse(Long addressId);
}

package com.aukcje.service.iface;

import com.aukcje.dto.AddressDTO;
import com.aukcje.entity.Address;
import com.aukcje.model.AddressModel;

import java.util.List;

public interface AddressService {

   Address getAddressFromAddressAddModel(AddressModel addressModel,
                                         Long userId);

   void updateAddress(AddressModel addressModel, Long userId);

   AddressDTO findById(Long addressId);

   List<AddressDTO> findByUserId(Long userId);

   boolean isAddressAssignedToUser(Long userId, Long addressId);

   void save(AddressModel addressAddModel, Long userId);

}

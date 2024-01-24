package com.aukcje.service.iface;

import com.aukcje.dto.AddressDTO;
import com.aukcje.entity.Address;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.model.AddressModel;

import java.util.List;

public interface AddressService {

   Address getAddressFromAddressAddModel(AddressModel addressModel,
                                         Long userId);
   void updateAddress(AddressModel addressModel, Long userId);

   AddressDTO findById(Long addressId);

   List<AddressDTO> findNotDeletedByUserId(Long userId);

   AddressDTO findNotDeletedById(Long addressId) throws AddressNotFoundException;

   void deleteAddress(Long addressId) throws AddressNotFoundException;

   boolean isAddressAssignedToUser(Long userId, Long addressId);

   boolean isAddressDeleted(AddressDTO addressDTO);

   void save(AddressModel addressAddModel, Long userId);

}

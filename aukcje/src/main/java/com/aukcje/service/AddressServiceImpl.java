package com.aukcje.service;

import com.aukcje.dto.AddressDTO;
import com.aukcje.dto.mapper.AddressDTOMapper;
import com.aukcje.entity.Address;
import com.aukcje.entity.Country;
import com.aukcje.entity.User;
import com.aukcje.model.AddressModel;
import com.aukcje.model.mapper.AddressAddModelMapper;
import com.aukcje.model.mapper.AddressEditModelMapper;
import com.aukcje.repository.AddressRepository;
import com.aukcje.repository.CountryRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public Address getAddressFromAddressAddModel(AddressModel addressModel, Long userId) {

        Address address = AddressAddModelMapper.instance.address(addressModel);

        address.setUser(userRepository.findById(userId).orElseThrow());
        address.setCountry(findByName(addressModel.getCountry()));

        return address;
    }

    @Override
    public void updateAddress(AddressModel addressModel, Long userId) {
        Address address = AddressEditModelMapper.instance.address(addressModel);
        address.setCountry(countryRepository.findByName(addressModel.getCountry()));

        address.setUser(findUserById(userId).orElseThrow());

        addressRepository.save(address);
    }

    @Override
    public AddressDTO findById(Long addressId) {
        return createAddressDTO(addressRepository.findById(addressId).orElse(null));
    }

    @Override
    public List<AddressDTO> findByUserId(Long userId) {
        return createAddressDTO(addressRepository.findByUserId(userId));
    }


    @Override
    public void save(AddressModel addressModel, Long userId){

        addressRepository.save( getAddressFromAddressAddModel(addressModel, userId) );
    }

    @Override
    public boolean isAddressAssignedToUser(Long userId, Long addressId){
        List<Address> addresses = addressRepository.findByUserId(userId);
        Optional<Address> address = addressRepository.findById(addressId);

        if(address.isEmpty()) return false;

        for(Address tempAddress : addresses){
            if(tempAddress.getId().equals(addressId)){
                return true;
            }
        }
        return false;
    }

    private List<AddressDTO> createAddressDTO(List<Address> addresses){
        List<AddressDTO> addressDTOS = new ArrayList<>();

        for(Address address : addresses){
            addressDTOS.add(AddressDTOMapper.instance.addressDTO(address));
        }
        return addressDTOS;
    }


    private AddressDTO createAddressDTO(Address address){
        return AddressDTOMapper.instance.addressDTO(address);
    }

    private Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    private Country findByName(String name){
        return countryRepository.findByName(name);
    }

}

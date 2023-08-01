package com.aukcje.dto.mapper;

import com.aukcje.dto.AddressDTO;
import com.aukcje.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressDTOMapper {

    AddressDTOMapper instance = Mappers.getMapper(AddressDTOMapper.class);

    @Mappings({})
    AddressDTO addressDTO(Address address);
}

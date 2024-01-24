package com.aukcje.model.mapper;

import com.aukcje.entity.Address;
import com.aukcje.model.AddressModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressAddModelMapper {

    AddressAddModelMapper instance = Mappers.getMapper(AddressAddModelMapper.class);

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Address address(AddressModel addressModel);
}

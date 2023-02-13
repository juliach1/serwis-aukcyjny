package com.aukcje.model.mapper;

import com.aukcje.entity.Address;
import com.aukcje.model.AddressModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressEditModelMapper {

    AddressEditModelMapper instance = Mappers.getMapper(AddressEditModelMapper.class);

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address address(AddressModel addressModel);
}

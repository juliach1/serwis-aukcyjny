package com.aukcje.dto.mapper;

import com.aukcje.dto.CountryDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.entity.Country;
import com.aukcje.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryDTOMapper {

    CountryDTOMapper instance = Mappers.getMapper(CountryDTOMapper.class);

    @Mappings({})
    CountryDTO countryDTO(Country country);
}

package com.aukcje.dto.mapper;

import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.entity.UserAuction;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAuctionDTOMapper {

    UserAuctionDTOMapper instance = Mappers.getMapper(UserAuctionDTOMapper.class);

    @Mappings({})
    UserAuctionDTO userAuctionDTO(UserAuction userAuction);

}

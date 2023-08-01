package com.aukcje.service.iface;

import com.aukcje.dto.CountryDTO;

import java.util.List;
public interface CountryService {

     List<CountryDTO> findAll();

     CountryDTO findByName(String string);

}

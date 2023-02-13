package com.aukcje.service.iface;

import com.aukcje.dto.CountryDTO;
import com.aukcje.entity.Country;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CountryService {

     List<CountryDTO> findAll();

     CountryDTO findByName(String string);
}

package com.aukcje.service;

import com.aukcje.dto.CountryDTO;
import com.aukcje.dto.mapper.CountryDTOMapper;
import com.aukcje.entity.Country;
import com.aukcje.repository.CountryRepository;
import com.aukcje.service.iface.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<CountryDTO> findAll() {
        return createCountryDTO(countryRepository.findAll());
    }

    @Override
    public CountryDTO findByName(String name) {
        return createCountryDTO(countryRepository.findByName(name));
    }

    private CountryDTO createCountryDTO(Country country){
        return CountryDTOMapper.instance.countryDTO(country);
    }

    private List<CountryDTO> createCountryDTO(List<Country> countries){
        List<CountryDTO> countryDTOS = new ArrayList<>();

        for(Country country : countries){
            countryDTOS.add(CountryDTOMapper.instance.countryDTO(country));
        }

        return countryDTOS;
    }
}

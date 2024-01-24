package com.aukcje.service;

import com.aukcje.dto.CountryDTO;
import com.aukcje.dto.mapper.CountryDTOMapper;
import com.aukcje.entity.Country;
import com.aukcje.repository.CountryRepository;
import com.aukcje.service.iface.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

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

    private List<CountryDTO> createCountryDTO(List<Country> countries) {
        List<CountryDTO> countryDTOS = new ArrayList<>();

        for(Country country : countries){
            countryDTOS.add(CountryDTOMapper.instance.countryDTO(country));
        }
        return countryDTOS;
    }
}

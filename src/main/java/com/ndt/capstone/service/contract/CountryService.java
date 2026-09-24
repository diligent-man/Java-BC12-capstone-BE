package com.ndt.capstone.service.contract;

import com.ndt.capstone.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    List<CountryDTO> getAllCountries();
}

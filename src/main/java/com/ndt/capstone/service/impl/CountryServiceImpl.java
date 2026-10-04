package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;


import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.CountryDTO;
import com.ndt.capstone.mapper.CountryMapper;
import com.ndt.capstone.repo.CountryRepo;
import com.ndt.capstone.config.props.cache.CountryCacheProps;

import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.CountryService;


@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepo countryRepo;

    private final CacheService cacheService;

    private final CountryCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<CountryDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> countryRepo
                .findAll()
                .stream()
                .map(CountryMapper::toDTO)
                .toList()
        );
    }
}

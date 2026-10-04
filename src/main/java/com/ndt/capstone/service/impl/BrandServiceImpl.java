package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.BrandDTO;
import com.ndt.capstone.mapper.BrandMapper;
import com.ndt.capstone.repo.BrandRepo;
import com.ndt.capstone.config.props.cache.BrandCacheProps;

import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.BrandService;


@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;

    private final CacheService cacheService;

    private final BrandCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> brandRepo
                .findAll()
                .stream()
                .map(BrandMapper::toDTO)
                .toList()
        );
    }
}

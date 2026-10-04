package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.SizeDTO;
import com.ndt.capstone.mapper.SizeMapper;
import com.ndt.capstone.repo.SizeRepo;
import com.ndt.capstone.config.props.cache.SizeCacheProps;

import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.SizeService;


@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepo sizeRepo;

    private final SizeCacheProps cacheProps;

    private final CacheService cacheService;


    @Override
    @Transactional(readOnly = true)
    public List<SizeDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> sizeRepo
                .findAll()
                .stream()
                .map(SizeMapper::toDTO)
                .toList()
        );
    }
}

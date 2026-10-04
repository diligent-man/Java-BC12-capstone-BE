package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.ColorDTO;
import com.ndt.capstone.mapper.ColorMapper;
import com.ndt.capstone.repo.ColorRepo;
import com.ndt.capstone.config.props.cache.ColorCacheProps;

import com.ndt.capstone.service.contract.ColorService;
import com.ndt.capstone.service.contract.CacheService;


@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepo colorRepo;

    private final CacheService cacheService;

    private final ColorCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<ColorDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> colorRepo
                .findAll()
                .stream()
                .map(ColorMapper::toDTO)
                .toList()
        );
    }
}

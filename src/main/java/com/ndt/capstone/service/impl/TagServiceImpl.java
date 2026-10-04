package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.TagDTO;
import com.ndt.capstone.mapper.TagMapper;
import com.ndt.capstone.repo.TagRepo;
import com.ndt.capstone.config.props.cache.TagCacheProps;

import com.ndt.capstone.service.contract.TagService;
import com.ndt.capstone.service.contract.CacheService;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;

    private final CacheService cacheService;

    private final TagCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> tagRepo
                .findAll()
                .stream()
                .map(TagMapper::toDTO)
                .toList()
        );
    }
}

package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.CategoryService;

import com.ndt.capstone.dto.CategoryDTO;
import com.ndt.capstone.mapper.CategoryMapper;
import com.ndt.capstone.repo.CategoryRepo;
import com.ndt.capstone.config.props.cache.CategoryCacheProps;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    private final CacheService cacheService;

    private final CategoryCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> categoryRepo
                .findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList()
        );
    }
}

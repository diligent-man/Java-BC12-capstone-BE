package com.ndt.capstone.service.impl;

import java.util.List;
import java.time.Duration;


import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.payment.PaymentMethodDTO;
import com.ndt.capstone.config.props.cache.PaymentCacheProps;
import com.ndt.capstone.mapper.payment.PaymentMethodMapper;
import com.ndt.capstone.repo.PaymentMethodRepo;

import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.PaymentService;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMethodRepo paymentMethodRepo;

    private final CacheService cacheService;

    private final PaymentCacheProps cacheProps;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.method().allKey(),
            Duration.ofMillis(cacheProps.method().all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> paymentMethodRepo
                .findAll()
                .stream()
                .map(PaymentMethodMapper::toDTO)
                .toList()
        );
    }
}

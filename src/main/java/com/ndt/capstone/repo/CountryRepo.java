package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.CountryEntity;


@Repository
public interface CountryRepo extends JpaRepository<CountryEntity, Integer> {
    Optional<CountryEntity> findByIso(String iso);
}

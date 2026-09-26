package com.ndt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ndt.capstone.entity.ColorEntity;


@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
}

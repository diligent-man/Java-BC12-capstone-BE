package com.ndt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ndt.capstone.entity.SizeEntity;


@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
}

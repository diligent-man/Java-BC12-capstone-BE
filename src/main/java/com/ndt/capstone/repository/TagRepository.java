package com.ndt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.TagEntity;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
}

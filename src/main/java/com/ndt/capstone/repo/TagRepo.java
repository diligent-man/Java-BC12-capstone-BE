package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.TagEntity;


@Repository
public interface TagRepo extends JpaRepository<TagEntity, Integer> {
    Optional<TagEntity> findByNameIgnoringCase(String name);
}

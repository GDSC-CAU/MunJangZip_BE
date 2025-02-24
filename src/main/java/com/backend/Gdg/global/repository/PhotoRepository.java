package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
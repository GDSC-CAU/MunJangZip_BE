package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 생략 가능하지만 붙이는 것도 가능
public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
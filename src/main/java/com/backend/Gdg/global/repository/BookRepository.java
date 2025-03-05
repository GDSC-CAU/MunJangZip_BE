package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
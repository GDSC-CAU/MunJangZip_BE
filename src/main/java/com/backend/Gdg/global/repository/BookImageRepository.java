package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookImageRepository extends JpaRepository<BookImage, Long> {
    BookImage findByBook_bookId(Long bookId);
}

package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
    List<Paragraph> findByBook_BookId(Long bookId);
}

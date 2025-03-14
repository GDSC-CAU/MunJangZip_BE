package com.backend.Gdg.global.repository;

import com.backend.Gdg.global.domain.entity.ParagraphImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParagraphImageRepository extends JpaRepository<ParagraphImage, Long> {
    Optional<ParagraphImage> findByParagraph_Book_BookId(Long bookId);
    Optional<ParagraphImage> findByParagraph_ParagraphId(Long paragraphId);
}

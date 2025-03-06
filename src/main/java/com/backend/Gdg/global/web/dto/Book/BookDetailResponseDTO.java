package com.backend.Gdg.global.web.dto.Book;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookDetailResponseDTO {
    private String title;
    private String author;
    private String coverImageUrl; // BookImage의 imageUrl
    private String ISBN;
    private String category;
    private List<ParagraphDTO> paragraph;

    @Getter
    @Builder
    public static class ParagraphDTO {
        private Long paragraph_id;
        private String content;
        private String ImageUrl; // (예: 필사 이미지 URL, 필요 시 설정)
        private String color;
        private String create_at; // yyyy-mm-dd 형식
    }
}
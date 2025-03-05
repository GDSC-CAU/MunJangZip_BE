package com.backend.Gdg.global.web.dto.Book;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class BookResponseDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookRegisterResponseDTO {
        private Long memberId;
        private Long bookId;
        private String title;
        private String author;
        private String ISBN;
        private LocalDate register_at;
        private String category;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookByCategoryResponseDTO {
        private Long categoryId;
        private String categoryName;
        private Long memberId;
        private List<BookCategoryListDTO> books; // 책 리스트 추가
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookCategoryListDTO {
        private Long bookId;
        private String bookImage;
        private String title;
        private String author;
    }

}

package com.backend.Gdg.global.web.dto.Book;

import lombok.*;

import java.time.LocalDate;

public class BookResponseDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookRegisterResponseDTO {
        private String title;
        private String author;
        private String ISBN;
        private LocalDate register_at;
        private String category;
    }

}

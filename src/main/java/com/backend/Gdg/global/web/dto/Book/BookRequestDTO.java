package com.backend.Gdg.global.web.dto.Book;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class BookRequestDTO {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookRegisterResquestDTO {
        private String title;
        private String author;
        private String ISBN;
        private String category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookImageRequestDTO {
        private MultipartFile image;
    }
}

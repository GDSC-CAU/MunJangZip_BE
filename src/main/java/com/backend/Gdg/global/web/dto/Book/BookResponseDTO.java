package com.backend.Gdg.global.web.dto.Book;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class BookResponseDTO {


    //메인 화면 조회 DTO
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MainBookResponseDTO {
        private Long categoryId;
        private String categoryName;
        private String recentBookCovers;
        private Long bookCount;
        private Long memoCount;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MainBookListResponseDTO {
        private String nickName;
        private List<MainBookResponseDTO> categoryList;
    }

    //BookRegister
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
        private String coverImageUrl;
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

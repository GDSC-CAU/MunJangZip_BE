package com.backend.Gdg.global.web.dto;

import lombok.Data;

@Data
public class IsbnResponseDto {
    private String title;
    private String author;
    private String coverImageUrl;
    private String isbn; // ISBN 값 추가
}

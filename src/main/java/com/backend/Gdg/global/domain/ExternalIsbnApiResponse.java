package com.backend.Gdg.global.domain;

import lombok.Data;

@Data
public class ExternalIsbnApiResponse {
    private String title;
    private String author;
    private String coverImageUrl;
    private String isbn; // ISBN 값 추가
}

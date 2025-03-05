package com.backend.Gdg.global.converter;

import com.backend.Gdg.global.domain.ExternalIsbnApiResponse;
import com.backend.Gdg.global.web.dto.IsbnResponseDto;
import org.springframework.stereotype.Component;

@Component
public class IsbnConverter {
    public IsbnResponseDto toDto(ExternalIsbnApiResponse externalResponse) {
        IsbnResponseDto dto = new IsbnResponseDto();
        dto.setTitle(externalResponse.getTitle());
        dto.setAuthor(externalResponse.getAuthor());
        dto.setCoverImageUrl(externalResponse.getCoverImageUrl());
        dto.setIsbn(externalResponse.getIsbn());
        return dto;
    }
}

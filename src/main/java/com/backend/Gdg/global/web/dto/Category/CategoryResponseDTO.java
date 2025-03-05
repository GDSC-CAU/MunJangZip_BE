package com.backend.Gdg.global.web.dto.Category;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private Long memberId;
}
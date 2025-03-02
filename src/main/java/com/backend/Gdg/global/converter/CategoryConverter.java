package com.backend.Gdg.global.converter;

import com.backend.Gdg.global.domain.entity.Category;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.web.dto.Category.CategoryRequestDTO;
import com.backend.Gdg.global.web.dto.Category.CategoryResponseDTO;

public class CategoryConverter {

    public static Category toCategory(CategoryRequestDTO request, Member member) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .member(member)
                .build();
    }

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .memberId(category.getMember().getMemberId())
                .build();
    }
}
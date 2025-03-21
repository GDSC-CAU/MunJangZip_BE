package com.backend.Gdg.global.service.CategoryService;

import com.backend.Gdg.global.web.dto.Category.CategoryRequestDTO;
import com.backend.Gdg.global.web.dto.Category.CategoryResponseDTO;

public interface CategoryService {
    CategoryResponseDTO addCategory(Long memberId, CategoryRequestDTO request);

    CategoryResponseDTO updateCategory(Long memberId ,Long categoryId, CategoryRequestDTO request);

    void deleteCategory(Long memberId,Long categoryId);
}
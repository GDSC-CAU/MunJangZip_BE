package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.security.handler.annotation.AuthUser;
import com.backend.Gdg.global.service.CategoryService.CategoryService;
import com.backend.Gdg.global.web.dto.Category.CategoryRequestDTO;
import com.backend.Gdg.global.web.dto.Category.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/category")
@Tag(name = "카테고리 API", description = "카테고리 관련 API입니다.")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @Operation(summary = "카테고리 추가 API", description = "사용자가 카테고리를 추가하는 API")
    public ApiResponse<CategoryResponseDTO> addCategory(
            @RequestBody CategoryRequestDTO request,
            @Parameter(hidden = true) @AuthUser Member member) {

        CategoryResponseDTO response = categoryService.addCategory(member.getMemberId(), request);
        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_OK, response);
    }


    @PatchMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정 API", description = "카테고리를 수정하는 API")
    public ApiResponse<CategoryResponseDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequestDTO request,
            @Parameter(hidden = true) @AuthUser Member member) {

        CategoryResponseDTO response = categoryService.updateCategory(member.getMemberId(),categoryId, request);
        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_OK, response);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제 API", description = "카테고리를 삭제하는 API")
    public ApiResponse<Void> deleteCategory(@PathVariable Long categoryId, @Parameter(hidden = true) @AuthUser Member member) {
        categoryService.deleteCategory(member.getMemberId(), categoryId);
        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_OK, null);
    }

}
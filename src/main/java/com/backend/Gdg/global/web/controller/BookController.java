package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/book")
@Tag(name = "책 API", description = "책 관련 API입니다.")
public class BookController {

    @GetMapping("")
    @Operation(summary = "메인 화면 조회 API", description = "책 목록을 조회하는 API")
    public ApiResponse<?> getMainBooks() {
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, null);
    }

    @PostMapping("/register")
    @Operation(summary = "책 등록 API", description = "새로운 책을 추가하는 API")
    public ApiResponse<?> registerBook() {
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, null);
    }

    @GetMapping("/detail/{book_id}")
    @Operation(summary = "책 상세 조회 API", description = "책의 상세 정보를 조회하는 API")
    public ApiResponse<?> getBookDetail(@PathVariable Long book_id) {
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, null);
    }
}
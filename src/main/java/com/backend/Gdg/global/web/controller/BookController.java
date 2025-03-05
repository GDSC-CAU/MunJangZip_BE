package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.security.handler.annotation.AuthUser;
import com.backend.Gdg.global.service.BookService.BookService;
import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Book.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private final BookService bookService;

    @PostMapping("/register")
    @Operation(summary = "책 등록 API", description = "새로운 책을 등록하는 API")
    public ApiResponse<BookResponseDTO.BookRegisterResponseDTO> registerBook(@RequestBody BookRequestDTO.BookRegisterResquestDTO request,@Parameter(hidden = true) @AuthUser Member member) {
        BookResponseDTO.BookRegisterResponseDTO response = bookService.registerBook(request, member.getMemberId());
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, response);
    }

    @PostMapping(path = "/{book_id}/book_cover", consumes = "multipart/form-data")
    @Operation(summary = "책 표지 이미지 등록 API", description = "책의 표지 이미지를 등록하는 API입니다.")
    public ApiResponse<?> uploadBookImage(
            @ModelAttribute BookRequestDTO.BookImageRequestDTO request,
            @PathVariable("book_id") Long bookId) {
        bookService.uploadBookImage(bookId, request);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, "책 표지 이미지 업로드 완료");
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "카테고리별 책 목록 조회 API", description = "해당 카테고리에 속한 책 목록을 조회하는 API")
    public ApiResponse<BookResponseDTO.BookByCategoryResponseDTO> getBooksByCategory(
            @PathVariable Long categoryId, @Parameter(hidden = true) @AuthUser Member member) {
        BookResponseDTO.BookByCategoryResponseDTO response = bookService.getBooksByCategory(categoryId, member.getMemberId());
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, response);
    }

    @GetMapping("/detail/{book_id}")
    @Operation(summary = "책 상세 조회 API", description = "책의 상세 정보를 조회하는 API")
    public ApiResponse<?> getBookDetail(@PathVariable Long book_id) {
        return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, null);
    }
}
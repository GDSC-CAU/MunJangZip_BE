package com.backend.Gdg.global.service.BookService;

import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Book.BookResponseDTO;

//이 interface를 BookServiceImpl에서 구체화
public interface BookService {
    //책 저장하는 서비스
    BookResponseDTO.BookRegisterResponseDTO registerBook(BookRequestDTO.BookRegisterResquestDTO request, Long memberId);

    //책 표지 업로드 사비스
    void uploadBookImage(Long bookId, BookRequestDTO.BookImageRequestDTO image);

    //카테고리별 책 조회 서비스
    BookResponseDTO.BookByCategoryResponseDTO getBooksByCategory(Long categoryId, Long memberId);
}





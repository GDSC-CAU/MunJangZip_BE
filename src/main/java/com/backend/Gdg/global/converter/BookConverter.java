package com.backend.Gdg.global.converter;

import com.backend.Gdg.global.domain.entity.Book;
import com.backend.Gdg.global.domain.entity.Category;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.repository.CategoryRepository;
import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Book.BookResponseDTO;

import java.time.LocalDate;

public class BookConverter {

    //BookRegisterResquestDTO를 바탕으로 Book엔티티 변환
    public static Book toBook(BookRequestDTO.BookRegisterResquestDTO request,
                              CategoryRepository categoryRepository,
                              Member member) {

        Category category = categoryRepository.findByCategoryName(request.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .ISBN(request.getISBN())
                .member(member)
                .category(category)
                .registerAt(LocalDate.now())
                .build();
    }

    // BookResposneDTO를 반환
    public static BookResponseDTO.BookRegisterResponseDTO toBookRegisterResponseDTO(Book book) {
        return BookResponseDTO.BookRegisterResponseDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .ISBN(book.getISBN())
                .register_at(book.getRegisterAt())
                .category(book.getCategory().getCategoryName())
                .build();
    }
}
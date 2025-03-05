package com.backend.Gdg.global.service.BookService;

import com.backend.Gdg.global.aws.s3.AmazonS3Manager;
import com.backend.Gdg.global.converter.BookConverter;
import com.backend.Gdg.global.domain.entity.*;
import com.backend.Gdg.global.repository.*;
import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Book.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookImageRepository bookImageRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    private final MemberRepository memberRepository;

    @Override
    public BookResponseDTO.BookRegisterResponseDTO registerBook(BookRequestDTO.BookRegisterResquestDTO request,Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Book book = BookConverter.toBook(request, categoryRepository, member);
        Book savedBook = bookRepository.save(book);

        return BookConverter.toBookRegisterResponseDTO(savedBook);
    }

    @Override
    public void uploadBookImage(Long bookId, BookRequestDTO.BookImageRequestDTO image){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
        String imageUrl = s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), image.getImage());

        // 기존 이미지가 있으면 삭제 후 새로 저장
        BookImage existingImage = bookImageRepository.findByBook_bookId(bookId);
        if (existingImage != null) {
            bookImageRepository.delete(existingImage);
        }

        BookImage bookImage = BookImage.builder()
                .book(book)
                .imageUrl(imageUrl)
                .build();

        bookImageRepository.save(bookImage);
    }

    @Override
    public BookResponseDTO.BookByCategoryResponseDTO getBooksByCategory(Long categoryId, Long memberId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        if (!category.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 카테고리에 접근할 수 없습니다.");
        }

        return BookConverter.toBookByCategoryResponseDTO(category);
    }
}
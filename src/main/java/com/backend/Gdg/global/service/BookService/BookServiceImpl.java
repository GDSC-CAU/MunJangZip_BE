package com.backend.Gdg.global.service.BookService;

import com.backend.Gdg.global.aws.s3.AmazonS3Manager;
import com.backend.Gdg.global.converter.BookConverter;
import com.backend.Gdg.global.domain.entity.*;
import com.backend.Gdg.global.repository.*;
import com.backend.Gdg.global.web.dto.Book.BookDetailResponseDTO;
import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Book.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final ParagraphImageRepository paragraphImageRepository;


    @Override
    public BookResponseDTO.MainBookListResponseDTO getMainBook(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        List<Category> categories = categoryRepository.findByMember_MemberId(memberId);
        List<BookResponseDTO.MainBookResponseDTO> categoryList = categories.stream()
                .map(BookConverter::toMainBookResponseDTO)
                .collect(Collectors.toList());
        return BookResponseDTO.MainBookListResponseDTO.builder()
                .nickName(member.getNickName())
                .libraryName(member.getLibraryName())
                .categoryList(categoryList)
                .build();
    }

    @Override
    public BookResponseDTO.BookRegisterResponseDTO registerBook(Long categoryId, BookRequestDTO.BookRegisterResquestDTO request,Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        Book book = BookConverter.toBook(request, category, member);
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


    @Override
    public BookDetailResponseDTO getBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        // `ParagraphImageRepository`에서 `Book`을 직접 찾는 것이 아니라, `Paragraph`를 통해 접근
        var paragraphs = book.getParagraphs().stream()
                .map(p -> {
                    // `Optional<ParagraphImage>`이므로 `.orElse(null)`을 사용하여 처리
                    Optional<ParagraphImage> paragraphImageOpt = paragraphImageRepository.findByParagraph_ParagraphId(p.getParagraphId());
                    String imageUrl = paragraphImageOpt.map(ParagraphImage::getImageUrl).orElse(null);

                    return BookDetailResponseDTO.ParagraphDTO.builder()
                            .paragraph_id(p.getParagraphId())
                            .content(p.getContent())
                            .color(p.getUserColor())
                            .createdAt(p.getCreatedAt())
                            .ImageUrl(imageUrl) // ✅ 수정된 부분
                            .build();
                })
                .collect(Collectors.toList());

        return BookDetailResponseDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImageUrl(book.getCoverImageUrl())
                .ISBN(book.getISBN())
                .category(book.getCategory().getCategoryName())
                .paragraph(paragraphs)
                .build();
    }
}
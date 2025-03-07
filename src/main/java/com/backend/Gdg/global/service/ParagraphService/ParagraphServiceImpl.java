package com.backend.Gdg.global.service.ParagraphService;

import com.backend.Gdg.global.domain.entity.Book;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.domain.entity.Paragraph;
import com.backend.Gdg.global.repository.BookRepository;
import com.backend.Gdg.global.repository.MemberRepository;
import com.backend.Gdg.global.repository.ParagraphRepository;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParagraphServiceImpl implements ParagraphService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final ParagraphRepository paragraphRepository;

    @Override
    public ParagraphResponseDTO addParagraph(ParagraphRequestDTO request, Long memberId) {
        // 책 및 회원 검증
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        // 현재 날짜 (yyyy-MM-dd)
        String currentDate = LocalDate.now().toString();

        Paragraph paragraph = Paragraph.builder()
                .book(book)
                .category(book.getCategory()) // 책과 동일한 카테고리 사용
                .member(member)
                .paragraph(request.getParagraph().getContent())
                .imageUrl(request.getParagraph().getImageURL())
                .userColor(request.getParagraph().getColor())
                .createAt(currentDate)
                .isLiked(false)
                .build();
        paragraphRepository.save(paragraph);

        // 해당 책의 모든 필사 조회 후 응답 DTO 매핑
        List<ParagraphResponseDTO.ParagraphDetail> paragraphDetails = paragraphRepository
                .findByBook_BookId(book.getBookId())
                .stream()
                .map(p -> ParagraphResponseDTO.ParagraphDetail.builder()
                        .paragraph_id(p.getParagraphId())
                        .content(p.getParagraph())
                        .imageUrl(p.getImageUrl())
                        .color(p.getUserColor())
                        .create_at(p.getCreateAt())
                        .build())
                .collect(Collectors.toList());

        return ParagraphResponseDTO.builder()
                .paragraph(paragraphDetails)
                .build();
    }
}

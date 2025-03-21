package com.backend.Gdg.global.service.ParagraphService;

import com.backend.Gdg.global.aws.s3.AmazonS3Manager;
import com.backend.Gdg.global.domain.entity.*;
import com.backend.Gdg.global.repository.*;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        LocalDateTime currentDate = LocalDateTime.now();

        Paragraph paragraph = Paragraph.builder()
                .book(book)
                .category(book.getCategory()) // 책과 동일한 카테고리 사용
                .member(member)
                .content(request.getParagraph().getContent())
                .userColor(request.getParagraph().getColor())
                .isLiked(false)
                .build();
        paragraphRepository.save(paragraph);

        // 해당 책의 모든 필사 조회 후 응답 DTO 매핑
        List<ParagraphResponseDTO.ParagraphDetail> paragraphDetails = paragraphRepository
                .findByBook_BookId(book.getBookId())
                .stream()
                .map(p -> ParagraphResponseDTO.ParagraphDetail.builder()
                        .paragraph_id(p.getParagraphId())
                        .content(p.getContent())
                        .imageUrl(p.getImageUrl())
                        .color(p.getUserColor())
                        .build())
                .collect(Collectors.toList());

        return ParagraphResponseDTO.builder()
                .paragraph(paragraphDetails)
                .build();
    }

    @Override
    public ParagraphResponseDTO updateParagraph(Long paragraphId, Long memberId, ParagraphUpdateRequestDTO request) {
        // 필사 존재 여부 및 수정 권한 검증: 필사 작성자만 수정 가능
        Paragraph paragraph = paragraphRepository.findById(paragraphId)
                .orElseThrow(() -> new IllegalArgumentException("해당 필사가 존재하지 않습니다."));
        if (!paragraph.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        // 요청 바디의 값으로 필드 업데이트 (텍스트나 이미지 중 하나가 null일 수 있음)
        if (request.getParagraph().getContent() != null) {
            paragraph.setContent(request.getParagraph().getContent());
        }
        if (request.getParagraph().getImageURL() != null) {
            paragraph.setImageUrl(request.getParagraph().getImageURL());
        }
        paragraph.setUserColor(request.getParagraph().getColor());
        // 생성일자는 그대로 유지

        // 수정 후 해당 책의 모든 필사 목록 조회 및 응답 DTO 매핑
        Book book = paragraph.getBook();
        List<ParagraphResponseDTO.ParagraphDetail> paragraphDetails = paragraphRepository
                .findByBook_BookId(book.getBookId())
                .stream()
                .map(p -> ParagraphResponseDTO.ParagraphDetail.builder()
                        .paragraph_id(p.getParagraphId())
                        .content(p.getContent())
                        .imageUrl(p.getImageUrl())
                        .color(p.getUserColor())
                        .build())
                .collect(Collectors.toList());

        return ParagraphResponseDTO.builder()
                .paragraph(paragraphDetails)
                .build();
    }

    @Override
    public ParagraphResponseDTO deleteParagraph(Long paragraphId, Long memberId) {
        // 필사 존재 여부 및 삭제 권한 검증: 오직 필사를 추가한 사용자만 삭제 가능
        Paragraph paragraph = paragraphRepository.findById(paragraphId)
                .orElseThrow(() -> new IllegalArgumentException("해당 필사가 존재하지 않습니다."));
        if (!paragraph.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        // 삭제 전 해당 필사가 속한 책 정보를 가져옴
        Book book = paragraph.getBook();

        // 필사 삭제
        paragraphRepository.delete(paragraph);

        // 삭제 후 해당 책의 남은 필사 목록 조회 및 응답 매핑
        List<ParagraphResponseDTO.ParagraphDetail> paragraphDetails = paragraphRepository
                .findByBook_BookId(book.getBookId())
                .stream()
                .map(p -> ParagraphResponseDTO.ParagraphDetail.builder()
                        .paragraph_id(p.getParagraphId())
                        .content(p.getContent())
                        .imageUrl(p.getImageUrl())
                        .color(p.getUserColor())
                        .build())
                .collect(Collectors.toList());

        return ParagraphResponseDTO.builder()
                .paragraph(paragraphDetails)
                .build();
    }

    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;
    private final ParagraphImageRepository paragraphImageRepository;

//    @Override
//    public void uploadParagraphImage(Long paragraphId, ParagraphRequestDTO.ParagraphImageRequestDTO image) {
//        Paragraph paragraph = paragraphRepository.findById(paragraphId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 필사가 존재하지 않습니다."));
//
//        String uuid = UUID.randomUUID().toString();
//        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
//        String imageUrl = s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), image.getImage());
//
//        // 기존 이미지가 존재하면 삭제 후 새로 저장
//        ParagraphImage existingImage = paragraphImageRepository.findByParagraph_ParagraphId(paragraphId);
//        if (existingImage != null) {
//            paragraphImageRepository.delete(existingImage);
//        }
//
//        // 새로운 ParagraphImage 저장
//        ParagraphImage paragraphImage = ParagraphImage.builder()
//                .paragraph(paragraph)
//                .imageUrl(imageUrl)
//                .build();
//
//        paragraphImageRepository.save(paragraphImage);
//    }

    @Override
    public void uploadParagraphImage(Long bookId, Long memberId, MultipartFile image, int color) {
        // 책 검증
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        // 회원 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        // 새로운 필사(Paragraph) 생성
        Paragraph paragraph = Paragraph.builder()
                .book(book)
                .category(book.getCategory())
                .member(member)
                .content(null)  // ✅ 이미지 기반 필사이므로 텍스트 없음
                .userColor(color)
                .isLiked(false)
                .build();
        paragraphRepository.save(paragraph);

        // 이미지 업로드 및 URL 획득
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
        String imageUrl = s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), image);

        // 기존 이미지가 존재하면 삭제 후 새로 저장
        // 문단 ID 기준으로 개별 이미지 저장
        Optional<ParagraphImage> existingImage = paragraphImageRepository.findByParagraph_ParagraphId(paragraph.getParagraphId());
        existingImage.ifPresent(paragraphImageRepository::delete);

        // 새로운 ParagraphImage 저장
        ParagraphImage paragraphImage = ParagraphImage.builder()
                .paragraph(paragraph)
                .imageUrl(imageUrl)
                .build();
        paragraphImageRepository.save(paragraphImage);
    }
}

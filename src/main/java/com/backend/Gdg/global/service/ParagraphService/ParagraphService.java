package com.backend.Gdg.global.service.ParagraphService;

import com.backend.Gdg.global.web.dto.Book.BookRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphUpdateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ParagraphService {
    // 필사 추가 기능
    ParagraphResponseDTO addParagraph(ParagraphRequestDTO request, Long memberId);

    // 필사 수정 기능
    ParagraphResponseDTO updateParagraph(Long paragraphId, Long memberId, ParagraphUpdateRequestDTO request);

    ParagraphResponseDTO deleteParagraph(Long paragraphId, Long memberId);

//    void uploadParagraphImage(Long paragraphId, ParagraphRequestDTO.ParagraphImageRequestDTO image);

    void uploadParagraphImage(Long bookId, Long memberId, MultipartFile image, int color);


    }

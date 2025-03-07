package com.backend.Gdg.global.service.ParagraphService;

import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;

public interface ParagraphService {
    // 필사 추가 기능
    ParagraphResponseDTO addParagraph(ParagraphRequestDTO request, Long memberId);
}

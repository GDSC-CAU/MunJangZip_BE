package com.backend.Gdg.global.web.dto.Paragraph;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ParagraphResponseDTO {
    private List<ParagraphDetail> paragraph;

    @Getter
    @Builder
    public static class ParagraphDetail {
        private Long paragraph_id;
        private String content;
        private String imageUrl;
        private int color;
        // yyyy-MM-dd 형식의 생성일자
        private String create_at;
    }
}

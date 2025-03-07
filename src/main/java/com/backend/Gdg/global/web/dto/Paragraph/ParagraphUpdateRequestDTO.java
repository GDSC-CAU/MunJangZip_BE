package com.backend.Gdg.global.web.dto.Paragraph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParagraphUpdateRequestDTO {
    private ParagraphDetail paragraph;

    @Getter
    @Setter
    public static class ParagraphDetail {
        private Long paragraph_id;
        private String content;
        private String imageURL;
        private int color;
    }
}

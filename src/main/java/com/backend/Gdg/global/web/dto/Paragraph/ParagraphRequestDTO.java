package com.backend.Gdg.global.web.dto.Paragraph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParagraphRequestDTO {
    // 필사가 추가될 책의 식별자
    private Long bookId;

    // 필사 상세 정보
    private ParagraphDetail paragraph;

    @Getter
    @Setter
    public static class ParagraphDetail {
        // 텍스트 필사인 경우 내용 입력, 사진 필사인 경우 null 가능
        private String content;
        // 사진 필사인 경우 S3에서 업로드한 URL 전달, 텍스트 필사는 null
        private String imageURL;
        // 사용자 지정 색상 코드가 int 타입으로 전달됨
        private int color;
    }
}

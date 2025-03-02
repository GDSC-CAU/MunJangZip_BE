package com.backend.Gdg.global.apiPayload.code.status;


import com.backend.Gdg.global.apiPayload.code.BaseCode;
import com.backend.Gdg.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 예시 응답
    EXAMPLE_OK(HttpStatus.OK, "EXAMPLE_1000", "성공입니다."),
    // 유저 관련 응답
    MEMBER_OK(HttpStatus.OK, "MEMBER_1000", "성공입니다."),
    BARCODE_OK(HttpStatus.OK, "BARCODE_1000", "성공입니다"),
    BOOK_OK(HttpStatus.OK, "BOOK_1000", "성공입니다"),
    PARAGRAPH_OK(HttpStatus.OK, "PARAGRAPH_1000", "성공입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}


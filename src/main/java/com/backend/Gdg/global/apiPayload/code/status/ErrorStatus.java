package com.backend.Gdg.global.apiPayload.code.status;


import com.backend.Gdg.global.apiPayload.code.BaseErrorCode;
import com.backend.Gdg.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // Auth 관련
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_LOGIN_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH_003", "올바른 아이디나 패스워드가 아닙니다."),
    NOT_EQUAL_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "리프레시 토큰이 다릅니다."),
    NOT_CONTAIN_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "해당하는 토큰이 저장되어있지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_006", "비밀번호가 일치하지 않습니다."),
    AUTH_INVALID_CODE(HttpStatus.UNAUTHORIZED, "AUTH_008", "코드가 유효하지 않습니다."),

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 회원 관련 에러 1000
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_1001", "사용자가 없습니다."),
    MEMBER_NAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER_1002", "이름입력은 필수 입니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1003", "이미 존재하는 유저입니다."),
    MEMBER_ID_NULL(HttpStatus.BAD_REQUEST, "MEMBER_1004", "사용자 아이디는 필수 입니다."),
    MEMBER_ADMIN_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "MEMBER_1005", "관리자 권한이 없습니다."),
    MEMBER_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "MEMBER_1006", "아이디나 비밀번호가 올바르지 않습니다."),
    MEMBER_WRONG_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER_1007", "이메일 형식이 올바르지 않습니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_1008", "비밀번호 형식이 올바르지 않습니다."),
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1009", "이미 가입된 이메일입니다."),
    MEMBER_NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1010", "이미 존재하는 닉네임입니다."),


    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}



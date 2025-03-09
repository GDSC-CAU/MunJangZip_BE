package com.backend.Gdg.global.web.dto.Member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class AuthRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EmailRegisterRequest {

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 200, message = "닉네임은 200자 이내로 입력해주세요.")
        private String nickname;

        @NotBlank(message = "도서관 이름은 필수입니다.")
        private String libraryName;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식을 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상 입력해야 합니다.")
        private String password;
    }


    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EmailLoginRequest {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식을 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter
    public static class RefreshToken {
        @JsonProperty("refresh_token")
        String refreshToken;
    }


}

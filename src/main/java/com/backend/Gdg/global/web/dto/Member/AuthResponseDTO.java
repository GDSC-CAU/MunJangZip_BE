package com.backend.Gdg.global.web.dto.Member;

import lombok.*;

public class AuthResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OAuthResponse {
        String accessToken;
        String refreshToken;
        Long memberId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EmailLoginResponse {
        String accessToken;
        String refreshToken;
        Long memberId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TokenRefreshResponse {
        String accessToken;
        String refreshToken;
    }
}

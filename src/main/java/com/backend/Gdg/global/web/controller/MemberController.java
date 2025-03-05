package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.service.MemberService.MemberCommandService;
import com.backend.Gdg.global.web.dto.Member.AuthRequestDTO;
import com.backend.Gdg.global.web.dto.Member.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/member")
@Tag(name = "회원 API", description = "회원 관련 API입니다.")
public class MemberController {
    private final MemberCommandService memberService;

    // 이메일 회원가입 API
    @PostMapping("/register")
    @Operation(summary = "이메일 회원가입 API", description = "이메일 회원가입을 진행하는 API 입니다.")
    public ApiResponse<?> emailSignUp(@RequestBody @Valid AuthRequestDTO.EmailRegisterRequest request){
        memberService.emailRegister(request);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 이메일 로그인 API
    @PostMapping("/login/email")
    @Operation(summary = "이메일 로그인 API", description = "이메일과 비밀번호로 로그인을 진행하는 API 입니다.")
    public ApiResponse<AuthResponseDTO.EmailLoginResponse> emailLogin(@RequestBody @Valid AuthRequestDTO.EmailLoginRequest request){
        AuthResponseDTO.EmailLoginResponse response = memberService.emailLogin(request);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, response);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "JWT Access Token 재발급 API",
            description = "Refresh Token을 검증하고 새로운 Access Token과 Refresh Token을 응답합니다.")
    public ApiResponse<AuthResponseDTO.TokenRefreshResponse> refresh(@RequestBody AuthRequestDTO.RefreshToken request) {
        AuthResponseDTO.TokenRefreshResponse response = memberService.refreshToken(request.getRefreshToken());
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, response);
    }
}

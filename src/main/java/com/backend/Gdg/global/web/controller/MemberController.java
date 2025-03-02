package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "사용자 회원가입을 처리하는 API")
    public ApiResponse<?> registerMember() {
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "사용자 로그인을 처리하는 API")
    public ApiResponse<?> loginMember() {
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }
}

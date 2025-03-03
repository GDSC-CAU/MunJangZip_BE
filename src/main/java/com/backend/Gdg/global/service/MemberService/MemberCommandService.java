package com.backend.Gdg.global.service.MemberService;

import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.web.dto.Member.AuthRequestDTO;
import com.backend.Gdg.global.web.dto.Member.AuthResponseDTO;

public interface MemberCommandService {

    Member findMemberById(Long memberId);
    void emailRegister(AuthRequestDTO.EmailRegisterRequest request);
    AuthResponseDTO.EmailLoginResponse emailLogin(AuthRequestDTO.EmailLoginRequest request);
    AuthResponseDTO.TokenRefreshResponse refreshToken(String refreshToken);
}
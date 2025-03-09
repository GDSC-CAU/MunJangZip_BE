package com.backend.Gdg.global.service.MemberService;


import com.backend.Gdg.global.apiPayload.code.status.ErrorStatus;
import com.backend.Gdg.global.apiPayload.exception.MemberException;
import com.backend.Gdg.global.converter.MemberConverter;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.repository.MemberRepository;
import com.backend.Gdg.global.security.provider.JwtTokenProvider;
import com.backend.Gdg.global.web.dto.Member.AuthRequestDTO;
import com.backend.Gdg.global.web.dto.Member.AuthResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }






    @Override
    @Transactional
    public void emailRegister(AuthRequestDTO.EmailRegisterRequest request) {
        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        // 회원 생성
        Member member = Member.builder()
                .nickName(request.getNickname())
                .libraryName(request.getLibraryName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        memberRepository.save(member);
    }


    @Override
    @Transactional
    public AuthResponseDTO.EmailLoginResponse emailLogin(AuthRequestDTO.EmailLoginRequest request) {
        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(ErrorStatus.MEMBER_LOGIN_FAIL);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());

        // DB에 refresh token 저장
        member.setRefreshToken(refreshToken);
        member.setAccessToken(accessToken);
        memberRepository.save(member);


        return MemberConverter.toEmailLoginResponse(accessToken, refreshToken, member);
    }

    @Override
    @Transactional
    public AuthResponseDTO.TokenRefreshResponse refreshToken(String refreshToken) {
        jwtTokenProvider.isTokenValid(refreshToken);
        Long id = jwtTokenProvider.getId(refreshToken);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        String newAccessToken = jwtTokenProvider.createAccessToken(id);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(id);
        member.updateToken(newAccessToken, newRefreshToken);
        memberRepository.save(member);
        return MemberConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);
    }

    // 랜덤 닉네임 생성
    private String makeNickname(){
        List<String> determiners = List.of(
                "예쁜", "멋진", "귀여운", "배고픈", "철학적인", "현학적인", "슬픈", "파란", "비싼", "밝은", "생각하는", "하얀"
        );

        List<String> animals = List.of(
                "토끼", "비버", "강아지", "부엉이", "여우", "호랑이", "문어", "고양이", "미어캣", "다람쥐", "수달", "곰"
        );

        Random random = new Random();
        String determiner = determiners.get(random.nextInt(determiners.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        return determiner + " " + animal;
    }
}
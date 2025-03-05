package com.backend.Gdg.global.security.principal;

import com.backend.Gdg.global.apiPayload.code.status.ErrorStatus;
import com.backend.Gdg.global.apiPayload.exception.AuthException;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));

        return new PrincipalDetails(member);
    }
}

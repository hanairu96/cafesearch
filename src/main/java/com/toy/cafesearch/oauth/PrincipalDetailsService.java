package com.toy.cafesearch.oauth;

import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        return new PrincipalDetails(member);
    }
}

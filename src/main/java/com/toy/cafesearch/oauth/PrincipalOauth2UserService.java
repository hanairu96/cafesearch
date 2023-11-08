package com.toy.cafesearch.oauth;

import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.oauth.provider.GoogleUserInfo;
import com.toy.cafesearch.oauth.provider.OAuth2UserInfo;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        //어느 Provider인지 확인
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }

        //여기서 데이터 받아서 멤버 객체 생성
        String provider = oAuth2UserInfo.getProvider(); //google
        String providerId = oAuth2UserInfo.getProviderId();
        String role = "ROLE_USER";

        String memberId = oAuth2UserInfo.getIdEmail();
        String password = bCryptPasswordEncoder.encode("1111"); //임의의 비밀번호
        String name = oAuth2UserInfo.getName();

        //이미 있는 회원인지 확인
        Optional<Member> memberEntity = memberRepository.findById(memberId);
        Member oauthMember = null;
        //없는 회원이면 자동 회원가입
        if(memberEntity.isEmpty()){
            oauthMember = Member.builder()
                    .memberId(memberId)
                    .password(password)
                    .name(name)
                    .nickname(name)
                    .birth(null)
                    .phone(null)
                    .gender(' ')
                    .email(memberId)
                    .address(null)
                    .provider(provider)
                    .providerId(providerId)
                    .role(role)
                    .build();
            memberRepository.save(oauthMember);
        }else {
            oauthMember = memberEntity.get();
        }

        return new PrincipalDetails(oauthMember, oauth2User.getAttributes());
    }
}

package com.toy.cafesearch.oauth;

import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.oauth.provider.GoogleUserInfo;
import com.toy.cafesearch.oauth.provider.KakaoUserInfo;
import com.toy.cafesearch.oauth.provider.NaverUserInfo;
import com.toy.cafesearch.oauth.provider.OAuth2UserInfo;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
        }else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        }else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
        }
        log.info("회원 정보: {}", oauth2User.getAttributes());

        //여기서 데이터 받아서 멤버 객체 생성
        String provider = oAuth2UserInfo.getProvider(); //google, naver, kakao
        String providerId = oAuth2UserInfo.getProviderId();
        String role = "ROLE_USER";

        String memberId = oAuth2UserInfo.getIdEmail();
        String password = bCryptPasswordEncoder.encode("1111"); //임의의 비밀번호
        String name = oAuth2UserInfo.getName();

        //이미 있는 회원인지 확인
        Optional<Member> memberEntity = memberRepository.findById(memberId);
        Member oauthMember = null;

        //없는 회원이면 자동 회원가입
        if(memberEntity.isEmpty() && provider!="naver") { //네이버가 아닐 때
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
        }else if (memberEntity.isEmpty() && provider=="naver") { //네이버일 때
            NaverUserInfo naverUserInfo = (NaverUserInfo)oAuth2UserInfo;
            String nickname = naverUserInfo.getNickname();
            Date birth = naverUserInfo.getBirth();
            String phone = naverUserInfo.getPhone();
            char gender = naverUserInfo.getGender();

            oauthMember = Member.builder()
                    .memberId(memberId)
                    .password(password)
                    .name(name)
                    .nickname(nickname)
                    .birth(birth)
                    .phone(phone)
                    .gender(gender)
                    .email(memberId)
                    .address(null)
                    .provider(provider)
                    .providerId(providerId)
                    .role(role)
                    .build();
            memberRepository.save(oauthMember);
        }else { //이미 있는 회원일 때
            oauthMember = memberEntity.get();
        }

        return new PrincipalDetails(oauthMember, oauth2User.getAttributes());
    }
}

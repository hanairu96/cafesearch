package com.toy.cafesearch.oauth;

import com.toy.cafesearch.dto.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes; //OAuth 유저 정보

    //일반 로그인시 생성자
    public PrincipalDetails(Member member){
        this.member = member;
    }

    //OAuth 로그인시 생성자
    public PrincipalDetails(Member member, Map<String, Object> attributes){
        this.member = member;
        this.attributes = attributes;
    }

    public Member getMember(){
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        return auth;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }
    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return member.getMemberId();
    }
}

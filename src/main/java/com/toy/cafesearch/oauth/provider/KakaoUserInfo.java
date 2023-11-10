package com.toy.cafesearch.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return Long.toString((long)attributes.get("id"));
    }

    @Override
    public String getIdEmail() {
        //attributes의 kakao_account 안에 있는 email을 가져옴
        return (String) ((Map)attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        return getIdEmail().split("@")[0]; //이메일의 @ 앞에 있는 아이디를 임시 이름으로 사용
    }
}

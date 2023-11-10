package com.toy.cafesearch.oauth.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getIdEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    public String getNickname(){
        String nickname = getName(); //닉네임의 기본값은 이름
        if (attributes.get("nickname")!=null){ //받아온 닉네임이 존재하면
            nickname = (String) attributes.get("nickname");
        }
        return nickname;
    }

    public char getGender(){
        char gender = ' ';
        if (attributes.get("gender")!=null){ //받아온 성별이 존재하면
            gender = ((String) attributes.get("gender")).charAt(0);
        }
        return gender;
    }

    public String getPhone(){
        String phone = null;
        if (attributes.get("mobile")!=null) { //받아온 전화번호가 존재하면
            String[] phoneSplit = ((String) attributes.get("mobile")).split("-");
            phone = phoneSplit[0] + phoneSplit[1] + phoneSplit[2];
        }
        return phone;
    }

    public Date getBirth() {
        int year = 2000, month = 1, day = 1; //생년월일 기본값은 2000년 1월 1일

        if (attributes.get("birthyear") != null && attributes.get("birthday") != null) { //생년과 생일 둘 다 있을 때
            year = Integer.valueOf((String) attributes.get("birthyear"));
            String[] monthDay = ((String) attributes.get("birthday")).split("-");
            month = Integer.valueOf(monthDay[0]);
            day = Integer.valueOf(monthDay[1]);
        }else if (attributes.get("birthyear") != null&&attributes.get("birthday") == null){ //생년은 있고 생일은 없을 때
            year = Integer.valueOf((String) attributes.get("birthyear"));
        }else if (attributes.get("birthyear") == null&&attributes.get("birthday") != null){ //생년은 없고 생일은 있을 때
            String[] monthDay = ((String) attributes.get("birthday")).split("-");
            month = Integer.valueOf(monthDay[0]);
            day = Integer.valueOf(monthDay[1]);
        }else { //생년월일 다 없을 때
            return null;
        }

        Calendar birth = Calendar.getInstance();
        birth.set(year, month, day);
        return new Date(birth.getTimeInMillis());
    }
}

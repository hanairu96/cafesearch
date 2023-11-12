package com.toy.cafesearch.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "member_id", length = 50)
    private String memberId;
    @Column(length = 200)
    private String password;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String nickname;
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Column(length = 20)
    private String phone;
    private char gender;
    @Column(length = 50)
    private String email;
    @Column(length = 200)
    private String address;

    @Column(length = 20)
    private String provider; //google, naver. kakao
    @Column(name = "provider_id", length = 100)
    private String providerId; //sub, id
    @Column(length = 20)
    private String role; //ROLE_USER
}
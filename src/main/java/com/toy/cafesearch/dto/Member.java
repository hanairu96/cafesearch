package com.toy.cafesearch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "member_id")
    private String memberId;
    private String password;
    private String name;
    private String nickname;
    private Date birth;
    private String phone;
    private char gender;
    private String email;
    private String address;

    private String provider; //google
    @Column(name = "provider_id")
    private String providerId; //sub
    private String role; //ROLE_USER

}
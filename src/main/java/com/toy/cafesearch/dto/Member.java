package com.toy.cafesearch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    private String MemberId;
    private String Password;
    private String name;
    private Date Birth;
    private String phone;
    private char gender;
    private String email;
    private String address;
}

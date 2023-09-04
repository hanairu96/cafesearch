package com.toy.cafesearch.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LikedCafe {
    private String cafeName;
    private String memberId;
    private Date likedDate;
}

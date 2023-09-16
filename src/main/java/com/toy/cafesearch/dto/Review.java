package com.toy.cafesearch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@DynamicUpdate
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @Column(name = "review_no")
    private int reviewNo;
    @Column(name = "cafe_name")
    private String cafeName;
    @Column(name = "member_id")
    private String memberId;
    private String title;
    private int star;
    @Column(name = "review_date")
    private Date reviewDate;
    private String content;
}

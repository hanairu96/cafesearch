package com.toy.cafesearch.dto;

import jakarta.persistence.*;
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
@SequenceGenerator(
        name = "REVIEW_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName = "REVIEW_SEQ", //시퀀스 이름
        initialValue = 1, //시작값
        allocationSize = 1 //메모리를 통해 할당할 범위 사이즈
)
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "REVIEW_SEQ_GEN"
    )  //기본키를 자동으로 1씩 증가시킴
    @Column(name = "review_no")
    private int reviewNo;
    @Column(name = "cafe_id", length = 120)
    private String cafeId;
    @Column(name = "cafe_name", length = 100)
    private String cafeName;
    @Column(name = "member_id", length = 50)
    private String memberId;
    @Column(name = "member_nickname", length = 50)
    private String memberNickname;
    @Column(length = 100)
    private String title;
    private int star;
    @Column(name = "review_date")
    private Date reviewDate;
    @Column(length = 2000)
    private String content;
}

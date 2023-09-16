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

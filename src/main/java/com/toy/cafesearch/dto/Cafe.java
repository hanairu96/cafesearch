package com.toy.cafesearch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {
    @Id
    @Column(name = "cafe_name", length = 100)
    private String cafeName;
    @Column(length = 1000)
    private String image;
    @Column(length = 200)
    private String address;
    private double star;
    private int mapx;
    private int mapy;
}

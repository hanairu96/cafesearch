package com.toy.cafesearch.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalReq {

    private String query = ""; //검색어
    private int display = 5; //한 번에 표시할 검색 결과 개수
    private int start = 1; //검색 시작 위치
    private String sort = "comment"; //검색 결과 정렬 방법-리뷰 개수순

    public MultiValueMap<String, String> toMultiValueMap(){
        var map = new LinkedMultiValueMap<String, String>();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);
        return map;
    }

}

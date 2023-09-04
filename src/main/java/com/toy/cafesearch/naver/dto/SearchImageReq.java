package com.toy.cafesearch.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchImageReq {

    private String query = ""; //검색어
    private int display = 5; //한 번에 표시할 검색 결과 개수
    private int start = 1; //검색 시작 위치
    private String sort = "sim"; //검색 결과 정렬 방법-정확도순
    private String filter = "all"; //크기별 검색 결과 필터-모든 이미지

    public MultiValueMap<String, String> toMultiValueMap(){
        var map = new LinkedMultiValueMap<String, String>();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);
        map.add("filter", filter);
        return map;
    }
}

package com.toy.cafesearch.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalRes {

    private Date lastBuildDate; //검색 결과를 생성한 시간
    private int total; //총 검색 결과 개수
    private int start; //검색 시작 위치
    private int display; //한 번에 표시할 검색 결과 개수
    private List<SearchLocalItem> items; //개별 검색 결과

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchLocalItem{
        private String title; //업체 이름
        private String link; //업체의 상세 정보 URL
        private String category; //업체의 분류 정보
        private String description; //업체에 대한 설명
        private String telephone; //값 반환 없음, 하위 호환성 위해 존재
        private String address; //지번 주소
        private String roadAddress; //도로명 주소
        private int mapx; //x좌표
        private int mapy; //y좌표
    }
}

package com.toy.cafesearch.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchImageRes {

    private Date lastBuildDate; //검색 결과를 생성한 시간
    private int total; //총 검색 결과 개수
    private int start; //검색 시작 위치
    private int display; //한 번에 표시할 검색 결과 개수
    private List<SearchImageItem> items; //개별 검색 결과

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchImageItem{
        private String title; //이미지가 검색된 문서의 제목
        private String link; //이미지의 URL
        private String thumbnail; //섬네일 이미지의 URL
        private String sizeheight; //이미지의 세로 크기
        private String sizewidth; //이미지의 가로 크기

    }
}

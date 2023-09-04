package com.toy.cafesearch.naver;

import com.toy.cafesearch.naver.dto.SearchImageReq;
import com.toy.cafesearch.naver.dto.SearchImageRes;
import com.toy.cafesearch.naver.dto.SearchLocalReq;
import com.toy.cafesearch.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverClient {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl; //지역 검색 요청 URL

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl; //이미지 검색 요청 URL

    //지역 검색 요청 내용 보내고 응답 받음
    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq){

        //Uri
        URI uri = UriComponentsBuilder.fromUriString(naverLocalSearchUrl) //호출할 서버 URL
                .queryParams(searchLocalReq.toMultiValueMap()) //요청할 내용
                .build()
                .encode()
                .toUri();

        //헤더
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        //RestTemplate
        //네이버 지역 검색 API URL에 데이터를 보내고 응답 받음
        ResponseEntity<SearchLocalRes> responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody(); //SearchLocalRes 반환
    }

    //이미지 검색 요청 내용 보내고 응답 받음
    public SearchImageRes searchImage(SearchImageReq searchImageReq) {

        //Uri
        URI uri = UriComponentsBuilder.fromUriString(naverImageSearchUrl) //호출할 서버 URL
                .queryParams(searchImageReq.toMultiValueMap()) //요청할 내용
                .build()
                .encode()
                .toUri();

        //헤더
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference responseType = new ParameterizedTypeReference<SearchImageRes>(){};

        //RestTemplate
        //네이버 이미지 검색 API URL에 데이터를 보내고 응답 받음
        ResponseEntity<SearchImageRes> responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody(); //SearchImageRes 반환
    }
}

package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.naver.NaverClient;
import com.toy.cafesearch.naver.dto.SearchImageReq;
import com.toy.cafesearch.naver.dto.SearchImageRes;
import com.toy.cafesearch.naver.dto.SearchLocalReq;
import com.toy.cafesearch.naver.dto.SearchLocalRes;
import com.toy.cafesearch.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final NaverClient naverClient;

    public Optional<Cafe> findByCafeId(String cafeId){
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        return cafe;
    }

    public List<Cafe> cafeListResult(String query){
        SearchLocalReq searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query+" 카페");

        SearchLocalRes searchLocalRes = naverClient.searchLocal(searchLocalReq);
        log.info("searchLocalRes: {}", searchLocalRes);

        List<SearchLocalRes.SearchLocalItem> localResItem = new ArrayList<>();
        searchLocalRes.getItems().stream()
                //.filter(item -> item.getCategory().contains("카페"))
                .forEach(cafeItem -> localResItem.add(cafeItem));
        log.info("localResItem: {}", localResItem);

        List<Cafe> cafeList = new ArrayList<>();
        for(SearchLocalRes.SearchLocalItem result : localResItem) {
            String resultTitle = result.getTitle();

            resultTitle = resultTitle.replace("<b>", "");
            resultTitle = resultTitle.replace("</b>", "");

            log.info("resultTitle: {}", resultTitle);
            log.info("searchLocalRes: {}", searchLocalRes);

            SearchImageReq searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(resultTitle);

            SearchImageRes searchImageRes = naverClient.searchImage(searchImageReq);
            log.info("searchImageRes: {}", searchImageRes);

            //카페 아이디는 카페명+X좌표+Y좌표
            String resultId = resultTitle + result.getMapx() + result.getMapy();

            Cafe cafe = new Cafe();
            cafe.setCafeId(resultId);
            cafe.setCafeName(resultTitle);

            //네이버 플레이스의 사진 링크에는 "pstatic"이 포함되어 있는 경우가 많아서 해당 키워드가 있는 링크가 있는지 찾음
            Optional<SearchImageRes.SearchImageItem> basicLink = searchImageRes.getItems().stream()
                    .filter(i -> i.getLink().contains("pstatic") == true).findFirst();

            String imageLink = "";
            if (!basicLink.isEmpty()) {
                imageLink = basicLink.get().getLink();
            } else if (!searchImageRes.getItems().stream().findFirst().isEmpty()) {
                imageLink = searchImageRes.getItems().stream().findFirst().get().getLink();
            }
            cafe.setImage(imageLink);

            //카페 테이블에 존재하지 않는 카페면 별점을 0으로, 존재하는 카페면 해당 별점으로 set 시킴
            if(cafeRepository.findById(resultId).isEmpty()){
                cafe.setStar(0);
            }else {
                cafe.setStar(cafeRepository.findById(resultId).get().getStar());
            }

            cafe.setAddress(result.getRoadAddress());
            cafe.setMapx(result.getMapx());
            cafe.setMapy(result.getMapy());

            cafeList.add(cafe);
        }
        return cafeList;
    }

    public Cafe cafeResult(String query, int index){
        List<Cafe> cafeList = cafeListResult(query);
        Cafe cafe = cafeList.get(index);
        return cafe;
    }
}

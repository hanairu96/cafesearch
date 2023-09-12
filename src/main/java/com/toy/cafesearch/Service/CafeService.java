package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.naver.NaverClient;
import com.toy.cafesearch.naver.dto.SearchImageReq;
import com.toy.cafesearch.naver.dto.SearchImageRes;
import com.toy.cafesearch.naver.dto.SearchLocalReq;
import com.toy.cafesearch.naver.dto.SearchLocalRes;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class CafeService {

    private final MemberRepository memberRepository;
    private final NaverClient naverClient;

    public List<Member> members(){
        List<Member> member = memberRepository.findAll();
        return member;
    }

    public Optional<Member> mb() {
        Optional<Member> m = memberRepository.findById("TESTID");
        return m;
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

            Cafe cafe = new Cafe();
            log.info("searchImageRes: {}", searchImageRes);
            cafe.setCafeName(resultTitle);

            Optional<SearchImageRes.SearchImageItem> basicLink = searchImageRes.getItems().stream()
                    .filter(i -> i.getLink().contains("pstatic") == true).findFirst();

            String imageLink = "";
            if (!basicLink.isEmpty()) {
                imageLink = basicLink.get().getLink();
            } else if (!searchImageRes.getItems().stream().findFirst().isEmpty()) {
                imageLink = searchImageRes.getItems().stream().findFirst().get().getLink();
            }
            cafe.setImage(imageLink);
            cafe.setStar(4.8);
            cafe.setAddress(result.getRoadAddress());

            cafeList.add(cafe);
        }
        return cafeList;
    }
}

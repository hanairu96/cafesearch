package com.toy.cafesearch.naver;

import com.toy.cafesearch.naver.dto.SearchLocalReq;
import com.toy.cafesearch.naver.dto.SearchLocalRes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverClientTest {

    @Autowired
    public NaverClient naverClient;

    @Test
    public void searchLocalTest(){
        SearchLocalReq search = new SearchLocalReq();
        search.setQuery("투썸 하안");

        SearchLocalRes result = naverClient.searchLocal(search);
        System.out.println(result);

        Assertions.assertNotNull(result);
    }
}

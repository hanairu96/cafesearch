package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Review;
import com.toy.cafesearch.naver.NaverClient;
import com.toy.cafesearch.repository.MemberRepository;
import com.toy.cafesearch.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReivewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final NaverClient naverClient;

    public List<Review> findAllByCafeName(String cafeName){
        List<Review> reviews = reviewRepository.findAllByCafeName(cafeName);
        return reviews;
    }
}

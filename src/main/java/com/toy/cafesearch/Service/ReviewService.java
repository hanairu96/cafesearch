package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Review;
import com.toy.cafesearch.repository.CafeRepository;
import com.toy.cafesearch.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.reducing;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;

    public List<Review> findAllByCafeId(String cafeId){
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);
        return reviews;
    }

    public Optional<Review> findByReviewNo(int reviewNo){
        Optional<Review> review = reviewRepository.findById(reviewNo);
        return review;
    }

    @Transactional
    public void saveReview(Review review, Cafe cafe){
        Optional<Cafe> optionalCafe = cafeRepository.findById(review.getCafeId());
        if(optionalCafe.isEmpty()){
            //첫 리뷰 등록시 카페 데이터도 생성됨
            cafe.setStar(review.getStar());
            Cafe newCafe = new Cafe(cafe.getCafeId(), cafe.getCafeName(), cafe.getImage(), cafe.getAddress(), cafe.getStar(), cafe.getMapx(), cafe.getMapy());
            cafeRepository.save(newCafe);
        }else {
            //리뷰 추가 등록시 카페의 평균 별점을 수정함
            int starSum = findAllByCafeId(review.getCafeId())
                    .stream()
                    .map(Review::getStar)
                    .collect(reducing(Integer::sum))
                    .get()
                    +review.getStar();
            int reviewCount = findAllByCafeId(review.getCafeId()).size() + 1;
            double averageStar = (double) starSum / reviewCount;
            log.info("starSum: {}", starSum);
            log.info("reviewCount: {}", reviewCount);
            log.info("averageStar: {}", averageStar);
            optionalCafe.get().setStar(Math.round(averageStar*100)/100.0); //소수점 2번째 자리까지만 저장
            cafeRepository.save(optionalCafe.get());
        }
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(int reviewNo, Review updateReview){
        Review review = findByReviewNo(reviewNo).get();

        //리뷰 수정시 카페 평균 별점도 수정
        Optional<Cafe> optionalCafe = cafeRepository.findById(review.getCafeId());
        int starSum = findAllByCafeId(review.getCafeId())
                .stream()
                .map(Review::getStar)
                .collect(reducing(Integer::sum))
                .get()
                - review.getStar() + updateReview.getStar();
        int reviewCount = findAllByCafeId(review.getCafeId()).size();
        double averageStar = (double) starSum / reviewCount;
        optionalCafe.get().setStar(Math.round(averageStar*100)/100.0);
        cafeRepository.save(optionalCafe.get());

        review.setTitle(updateReview.getTitle());
        review.setStar(updateReview.getStar());
        review.setContent(updateReview.getContent());
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(int reviewNo){
        //리뷰 삭제시 카페 평균 별점도 수정
        Review review = findByReviewNo(reviewNo).get();
        String reviewCafeId = review.getCafeId();
        Optional<Cafe> optionalCafe = cafeRepository.findById(reviewCafeId);
        int starSum = findAllByCafeId(reviewCafeId)
                .stream()
                .map(Review::getStar)
                .collect(reducing(Integer::sum))
                .get()
                - review.getStar();
        int reviewCount = findAllByCafeId(reviewCafeId).size() - 1;
        double averageStar = (double) starSum / reviewCount;
        optionalCafe.get().setStar(Math.round(averageStar*100)/100.0);
        cafeRepository.save(optionalCafe.get());

        reviewRepository.deleteById(reviewNo);

        //리뷰가 존재하지 않으면 해당 카페 데이터도 삭제
        if (findAllByCafeId(reviewCafeId).isEmpty()){
            cafeRepository.deleteById(reviewCafeId);
        }
    }
}

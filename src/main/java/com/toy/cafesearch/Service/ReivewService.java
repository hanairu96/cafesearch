package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Review;
import com.toy.cafesearch.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReivewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findAllByCafeName(String cafeName){
        List<Review> reviews = reviewRepository.findAllByCafeName(cafeName);
        return reviews;
    }

    public Optional<Review> findByReviewNo(int reviewNo){
        Optional<Review> review = reviewRepository.findById(reviewNo);
        return review;
    }

    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public void updateReview(int reviewNo, Review updateReview){
        Review review = findByReviewNo(reviewNo).get();
        review.setTitle(updateReview.getTitle());
        review.setStar(updateReview.getStar());
        review.setContent(updateReview.getContent());
        reviewRepository.save(review);
    }

    public void deleteReview(int reviewNo){
        reviewRepository.deleteById(reviewNo);
    }
}

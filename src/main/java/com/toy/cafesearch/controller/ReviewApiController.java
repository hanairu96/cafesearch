package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.ReviewService;
import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cafe/review/")
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewNo}")
    public Review getReview(@PathVariable int reviewNo){
        Optional<Review> optionalReview = reviewService.findByReviewNo(reviewNo);
        if (optionalReview.isEmpty()){
            return null;
        }else {
            return optionalReview.get();
        }
    }

    @PostMapping("/")
    public void postReview(@RequestBody Review review, Cafe cafe){
        reviewService.saveReview(review, cafe);
    }

    @PutMapping("/{reviewNo}")
    public void updateReview(@PathVariable int reviewNo, @RequestBody Review updateReview){
        reviewService.updateReview(reviewNo, updateReview);
    }

    @DeleteMapping("/{reviewNo}")
    public void deleteReview(@PathVariable int reviewNo){
        reviewService.deleteReview(reviewNo);
    }

}

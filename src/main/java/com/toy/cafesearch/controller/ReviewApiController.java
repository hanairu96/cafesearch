package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.CafeService;
import com.toy.cafesearch.Service.ReviewService;
import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cafe/review/")
public class ReviewApiController {

    private final ReviewService reviewService;
    private final CafeService cafeService;

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
    public int postReview(@RequestParam(value = "postElements[]") List<String> elements){
        String title = elements.get(0);
        String cafeName = elements.get(1);
        int star = Integer.parseInt(elements.get(2));
        String content = elements.get(3);
        String query = elements.get(4);
        int index = Integer.parseInt(elements.get(5));
        Object loginMember = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate localDate = LocalDate.now();

        Review review = Review.builder()
                .cafeName(cafeName)
                .memberId(((Member)loginMember).getMemberId())
                .title(title)
                .star(star)
                .reviewDate(Date.valueOf(localDate))
                .content(content).build();

        Cafe cafe = cafeService.cafeResult(query, index);

        log.info("review: {}", review);
        log.info("cafe: {}", cafe);

        reviewService.saveReview(review, cafe);

        return 0;
    }

    @PutMapping("/{reviewNo}")
    public void updateReview(@RequestBody List<String> elements, @PathVariable int reviewNo){
        String title = elements.get(0);
        String cafeName = elements.get(1);
        int star = Integer.parseInt(elements.get(2));
        String content = elements.get(3);
        String query = elements.get(4);
        int index = Integer.parseInt(elements.get(5));
        Object loginMember = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate localDate = LocalDate.now();

        Review updateReview = Review.builder()
                .cafeName(cafeName)
                .memberId(((Member)loginMember).getMemberId())
                .title(title)
                .star(star)
                .reviewDate(Date.valueOf(localDate))
                .content(content).build();

        reviewService.updateReview(reviewNo, updateReview);
    }

    @DeleteMapping("/{reviewNo}")
    public void deleteReview(@PathVariable int reviewNo){
        reviewService.deleteReview(reviewNo);
    }

}

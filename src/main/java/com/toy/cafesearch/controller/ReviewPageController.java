package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.ReviewService;
import com.toy.cafesearch.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/cafe")
public class ReviewPageController {

    private final ReviewService reviewService;

    @GetMapping("/reviewWrite")
    public ModelAndView reviewWrite(ModelAndView mv, String query, String index, String name){
        mv.addObject("query", query);
        mv.addObject("index", index);
        mv.addObject("cafeName", name);
        mv.setViewName("reviewWrite");
        return mv;
    }

    @GetMapping("/reviewUpdate")
    public ModelAndView reviewUpdate(ModelAndView mv, String query, String index, int reviewNo){
        mv.addObject("query", query);
        mv.addObject("index", index);
        Review review = reviewService.findByReviewNo(reviewNo).get();
        mv.addObject("cafeName", review.getCafeName());
        mv.addObject("review", review);
        mv.setViewName("reviewUpdate");
        return mv;
    }
}

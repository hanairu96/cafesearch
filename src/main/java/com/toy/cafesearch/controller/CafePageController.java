package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.CafeService;
import com.toy.cafesearch.Service.ReviewService;
import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/cafe")
public class CafePageController {

    private final CafeService cafeService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/searchResult")
    public ModelAndView searchResult(ModelAndView mv , @RequestParam String keyword){

        List<Cafe> cafeList = cafeService.cafeListResult(keyword);
        log.info("{}",cafeList);
        mv.addObject("cafeList", cafeList);
        mv.addObject("query", keyword);
        mv.setViewName("searchResult");
        return mv;
    }

    @GetMapping("/cafeDetail")
    public ModelAndView cafeDetail(ModelAndView mv,
                                   @RequestParam String query,
                                   @RequestParam String index,
                                   @RequestParam String name,
                                   @RequestParam String mapx,
                                   @RequestParam String mapy
    ){
        //카페 아이디
        String id = name + mapx + mapy;

        Cafe detailCafe = cafeService.cafeResult(query, Integer.parseInt(index));
        if (!cafeService.findByCafeId(id).isEmpty()){
            detailCafe = cafeService.findByCafeId(id).get();
        }
        List<Review> reviews = reviewService.findAllByCafeId(id);
        log.info("카페: {}", detailCafe);
        log.info("리뷰: {}", reviews);
        mv.addObject("detailCafe", detailCafe);
        mv.addObject("reviews", reviews);
        mv.setViewName("cafeDetail");
        return mv;
    }
}

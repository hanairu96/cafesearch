package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.CafeService;
import com.toy.cafesearch.dto.Cafe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cafe")
public class PageController {

    @Autowired
    CafeService cafeService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/searchResult")
    public ModelAndView searchResult(ModelAndView mv , @RequestParam String keyword){

        List<Cafe> cafeList = cafeService.cafeListResult(keyword);
        log.info("{}",cafeList);
        mv.addObject("cafeList", cafeList);
        mv.setViewName("searchResult");
        return mv;
    }

    @GetMapping("/cafeDetail")
    public ModelAndView cafeDetail(ModelAndView mv,
                                   @RequestParam String name,
                                   @RequestParam String image,
                                   @RequestParam String address,
                                   @RequestParam double star){
        Cafe detailCafe = new Cafe(name, image, address, star);
        log.info("{}", detailCafe);
        mv.addObject("detailCafe", detailCafe);
        mv.setViewName("cafeDetail");
        return mv;
    }

}

package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.CafeService;
import com.toy.cafesearch.Service.MemberService;
import com.toy.cafesearch.Service.ReviewService;
import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/cafe")
public class PageController {

    private final CafeService cafeService;
    private final MemberService memberService;
    private final ReviewService reivewService;
    private final BCryptPasswordEncoder passwordEncoder;

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
                                   @RequestParam String name){

        Cafe detailCafe = cafeService.cafeResult(query, Integer.parseInt(index));
//        Cafe detailCafe = new Cafe(name, image, address, star);
        if (!cafeService.findByCafeName(name).isEmpty()){
            detailCafe = cafeService.findByCafeName(name).get();
        }
        List<Review> reviews = reivewService.findAllByCafeName(name);
        log.info("카페: {}", detailCafe);
        log.info("리뷰: {}", reviews);
        mv.addObject("detailCafe", detailCafe);
        mv.addObject("reviews", reviews);
        mv.setViewName("cafeDetail");
        return mv;
    }

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
        Review review = reivewService.findByReviewNo(reviewNo).get();
        mv.addObject("cafeName", review.getCafeName());
        mv.addObject("review", review);
        mv.setViewName("reviewUpdate");
        return mv;
    }

    @GetMapping("/member/loginPage/")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/member/enrollMember")
    public String enrollMember(){
        return "enrollMember";
    }

    //회원가입
    @PostMapping("/member/enrollMemberEnd")
    public String enrollMemberEnd(Member m, String year, String month, String day,
                                String inputAddressPostcode, String inputAddressAddress, String inputAddressDetailAddress) throws ParseException {

        //문자열로 받아온 생년월일을 Date 타입으로 변환
        String dateStr = year + "/" + month + "/" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
        Date date = formatter.parse(dateStr);

        //주소를 하나로 합침
        String address = "(" + inputAddressPostcode + ")" + inputAddressAddress + ", " + inputAddressDetailAddress;

        m.setBirth(date);
        m.setAddress(address);

        //패스워드 암호화
        String encodePassword = passwordEncoder.encode(m.getPassword());
        m.setPassword(encodePassword);

        memberService.saveMember(m);

        return "redirect:/cafe/";
    }

    @GetMapping("/member/loginSuccess")
    public String loginSuccess(Model model){
        Object member = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loginMember", (Member)member);
        log.info("loginMember: {}", model.getAttribute("loginMember"));
        return "redirect:/cafe/";
    }
    @GetMapping("/member/loginFailure")
    public String loginFailure(Model model){
        log.info("로그인 실패");
        return "redirect:/cafe/member/loginPage/";
    }
}

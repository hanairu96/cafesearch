package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.CafeService;
import com.toy.cafesearch.Service.MemberService;
import com.toy.cafesearch.dto.Cafe;
import com.toy.cafesearch.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class PageController {

    private final CafeService cafeService;
    private final MemberService memberService;
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
        mv.setViewName("searchResult");
        return mv;
    }

    @PostMapping("/cafeDetail")
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
}

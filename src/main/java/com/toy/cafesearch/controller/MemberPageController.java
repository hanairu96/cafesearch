package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.MemberService;
import com.toy.cafesearch.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/cafe/member")
public class MemberPageController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/loginPage/")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/enrollMember")
    public String enrollMember(){
        return "enrollMember";
    }

    //회원가입
    @PostMapping("/enrollMemberEnd")
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

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model){
        Object member = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loginMember", (Member)member);
        log.info("loginMember: {}", model.getAttribute("loginMember"));
        return "redirect:/cafe/";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(Model model){
        log.info("로그인 실패");
        return "redirect:/cafe/member/loginPage/";
    }
}

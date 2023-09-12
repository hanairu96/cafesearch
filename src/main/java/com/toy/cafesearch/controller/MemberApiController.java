package com.toy.cafesearch.controller;

import com.toy.cafesearch.Service.MemberService;
import com.toy.cafesearch.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cafe/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/idDuplicateCheck")
    public String idDuplicateCheck(String inputId){

        Optional<Member> member = memberService.findMemberById(inputId);
        String memberId = "";
        if (member.isEmpty()){
            memberId = null;
        }else {
            memberId = member.get().getMemberId();
        }

        log.info("id: {}",memberId);

        return memberId;
    }

}

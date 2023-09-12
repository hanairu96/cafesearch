package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.naver.NaverClient;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final NaverClient naverClient;

    public Optional<Member> findMemberById(String id){

        Optional<Member> member = memberRepository.findById(id);

        return member;
    }

    public void saveMember(Member member){
        memberRepository.save(member);
    }



}

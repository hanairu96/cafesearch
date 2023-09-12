package com.toy.cafesearch.Service;

import com.toy.cafesearch.dto.Member;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findMemberById(String id){

        Optional<Member> member = memberRepository.findById(id);

        return member;
    }

    public void saveMember(Member member){
        memberRepository.save(member);
    }



}

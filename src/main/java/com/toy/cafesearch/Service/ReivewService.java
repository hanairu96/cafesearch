package com.toy.cafesearch.Service;

import com.toy.cafesearch.naver.NaverClient;
import com.toy.cafesearch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReivewService {

    private final MemberRepository memberRepository;
    private final NaverClient naverClient;
}

package me.jhkim.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired // bean 주입 (자동 할당)
    TestRepository memberRepository;
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}

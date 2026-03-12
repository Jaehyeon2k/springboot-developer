package me.jhkim.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // @Controller + ResponseBody
// 모든 메서드가 데이터를 리턴한다
// @Controller
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers() {
        return testService.getAllMembers();
    }
}

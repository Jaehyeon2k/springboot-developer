package me.jhkim.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // @Controller + ResponseBody
// 모든 메서드가 데이터를 리턴한다
// @Controller
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public  ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(testService.getAllMembers());
    }
    @GetMapping("/test1")
    public String test() {
        return "Hello World";
    }


    @PostMapping("/test") // 자원을 요청할때
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(testService.saveMember(member));
    }
}

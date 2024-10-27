package com.example.chanhyunguniversity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ChanhyungUniversityApplicationTests {
    @Test
    void generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("ㅇㅁㄴㅇㄴㅇㅁㄹ");
        System.out.println(encoder.encode("111"));
    }
    @Test
    void contextLoads() {
        // 간단한 테스트로 시작
        assert(true);
    }
}

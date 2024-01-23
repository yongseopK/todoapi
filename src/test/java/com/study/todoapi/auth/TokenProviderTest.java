package com.study.todoapi.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Test
    @DisplayName("토큰 서명에 필요한 비밀키 해시값 생성하기")
    void makeSecretKey() {
        SecureRandom random = new SecureRandom();
        // 512bit == 64byte
        byte[] key = new byte[64];

        random.nextBytes(key);
        String encoded = Base64.getEncoder().encodeToString(key);

        System.out.println("\n\n\n\n");
        System.out.println("encoded = " + encoded);
        System.out.println("\n\n\n\n");
    }


}
package com.study.todoapi.user.service;

import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.dto.response.UserSignUpResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입을하면 비밀번호가 인코딩되어 DB에 저장됨")
    void saveTest() {
        //given
        UserSignUpRequestDTO dto = UserSignUpRequestDTO.builder()
                .email("kk0@kakao.com")
                .password("bbb1234!")
                .userName("외계인")
                .build();
        //when
        UserSignUpResponseDTO responseDTO = userService.create(dto);

        //then
        assertEquals("외계인", responseDTO.getUserName());

        System.out.println("\n\n\n\n");
        System.out.println("responseDTO = " + responseDTO);
        System.out.println("\n\n\n\n");

    }


}
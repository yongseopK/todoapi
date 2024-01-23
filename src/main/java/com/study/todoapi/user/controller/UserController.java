package com.study.todoapi.user.controller;

import com.study.todoapi.exception.DuplicatedEmailException;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.dto.request.LoginRequestDTO;
import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.dto.response.LoginResponseDTO;
import com.study.todoapi.user.dto.response.UserSignUpResponseDTO;
import com.study.todoapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
//@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    // 회원가입 요청처리
    @PostMapping
    public ResponseEntity<?> signUp(
            @Validated @RequestBody UserSignUpRequestDTO dto,
            BindingResult result
    ) {

        log.info("api/auth POST! - {}", dto);

        if(result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest().body(result.getFieldError());
        }

        try {
            UserSignUpResponseDTO responseDTO = userService.create(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (NoRegisteredArgumentsException e) {
            log.warn("필수 가입 정보를 전달받지 못했습니다.");
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("이메일이 중복되었습니다.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 이메일 중복확인 요청처리
    @GetMapping("/check")
    public ResponseEntity<?> check(String email) {
        boolean flag = userService.isDuplicateEmail(email);
        log.debug("{} 중복여부 - {}", email, flag);

        return ResponseEntity.ok().body(flag);
    }

    // 로그인 요청처리
    @PostMapping("/signin")
    public ResponseEntity<?> signIn (
            @Validated @RequestBody LoginRequestDTO dto
            ) {
        try {
            LoginResponseDTO responseDTO = userService.authenticate(dto);
            log.info("login success!! by {}", responseDTO.getUserName());
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


















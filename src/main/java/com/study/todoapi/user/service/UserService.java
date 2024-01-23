package com.study.todoapi.user.service;

import com.study.todoapi.auth.TokenProvider;
import com.study.todoapi.exception.DuplicatedEmailException;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.dto.request.LoginRequestDTO;
import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.dto.response.LoginResponseDTO;
import com.study.todoapi.user.dto.response.UserSignUpResponseDTO;
import com.study.todoapi.user.entity.User;
import com.study.todoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입 처리
    public UserSignUpResponseDTO create(UserSignUpRequestDTO dto) {
        if(dto == null) {
            throw new NoRegisteredArgumentsException("회원가입 입력정보가 없습니다.");
        }
        String email = dto.getEmail();

        if(userRepository.existsByEmail(email)) {
            log.warn("이메일이 중복되었습니다. : {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }

        User saved = userRepository.save(dto.toEntity(passwordEncoder));

        log.info("회원가입 성공 saved user - {}", saved);

        return new UserSignUpResponseDTO(saved); // 회원가입 정보를 클라이언트에게 리턴
    }

    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    // 회원인증
    public LoginResponseDTO authenticate(final LoginRequestDTO dto) {

        // 이메일을 통해 회원정보를 조회함
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("가입된 회원이 아닙니다."));

        // 패스워드 검증
        String inputPassword = dto.getPassword();       // 입력 비번
        String encodedPassword = user.getPassword();    // DB에 저장된 비번

        if(!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 후 이제 어떻게 할 것인가??? 세션에 저장할 것인가? 토큰을 발급할 것인가?
        String token = tokenProvider.createToken(user);

        // 클라이언트에게 토큰을 발급해서 제공
        return new LoginResponseDTO(user, token);
    }

}














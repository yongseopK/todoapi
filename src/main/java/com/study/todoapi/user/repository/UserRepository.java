package com.study.todoapi.user.repository;

import com.study.todoapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 쿼리 메서드
    // 이메일로 회원정보 조회
    //@Query("SELECT u from User u where u.email=?1")
    Optional<User> findByEmail(String email);

    // 이메일 중복체크
    //@Query("select count(*) FROM User u where u.email=?1")
    boolean existsByEmail(String email);
}

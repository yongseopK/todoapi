package com.study.todoapi.todo.service;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional  // JPA 사용시 필수
public class TodoService {

    private final TodoRepository todoRepository;

    // 할 일 등록
    public void create(TodoCreateRequestDTO dto) {
        todoRepository.save(dto.toEntity());
        log.info("새로운 할 일이 저장되었습니다. 제목 : {}", dto.getTitle());

    }
}

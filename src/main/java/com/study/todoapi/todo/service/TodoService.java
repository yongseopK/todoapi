package com.study.todoapi.todo.service;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.dto.rseponse.TodoDetailResponseDTO;
import com.study.todoapi.todo.dto.rseponse.TodoListResponseDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional  // JPA 사용시 필수
public class TodoService {

    private final TodoRepository todoRepository;

    // 할 일 등록
    public TodoListResponseDTO create(TodoCreateRequestDTO dto) {
        todoRepository.save(dto.toEntity());
        log.info("새로운 할 일이 저장되었습니다. 제목 : {}", dto.getTitle());

        return retrieve();
    }

    // 할 일 목록 불러오기
    public TodoListResponseDTO retrieve() {
        List<Todo> todoList = todoRepository.findAll();

        // 엔터티 리스트를 dto리스트로 매핑하고 리턴
        List<TodoDetailResponseDTO> dtoList = todoList.stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());
        return TodoListResponseDTO.builder().todos(dtoList).build();
    }
}

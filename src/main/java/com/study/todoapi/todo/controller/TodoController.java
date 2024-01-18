package com.study.todoapi.todo.controller;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.dto.rseponse.TodoDetailResponseDTO;
import com.study.todoapi.todo.dto.rseponse.TodoListResponseDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    // 할 일 등록 요청
    @PostMapping
    public ResponseEntity<?> createTodo(
            @Validated @RequestBody TodoCreateRequestDTO dto,
            BindingResult result
    ) {
        if(result.hasErrors()) {
            log.warn("DTO검증 에러 : {}", result.getFieldError());
            return ResponseEntity.badRequest().body(result.getFieldError());
        }

        try {
            TodoListResponseDTO dtoList = todoService.create(dto);
            return ResponseEntity.ok().body(dtoList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    // 할 일 목록조회 요청
    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        log.info("/api/todos GET");

        TodoListResponseDTO retrieve = todoService.retrieve();

        return ResponseEntity.ok().body(retrieve);
    }
}

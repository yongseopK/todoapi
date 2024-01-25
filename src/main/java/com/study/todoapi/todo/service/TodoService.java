package com.study.todoapi.todo.service;

import com.study.todoapi.todo.dto.request.TodoCheckRequestDTO;
import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.dto.rseponse.TodoDetailResponseDTO;
import com.study.todoapi.todo.dto.rseponse.TodoListResponseDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.repository.TodoRepository;
import com.study.todoapi.user.entity.User;
import com.study.todoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional  // JPA 사용시 필수
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 할 일 등록
    public TodoListResponseDTO create(TodoCreateRequestDTO dto, String email) {

        Optional<User> foundUser = userRepository.findByEmail(email);

        foundUser.ifPresent(user -> {
            Todo todo = todoRepository.save(dto.toEntity(user));
            // 양방향 매핑에서는 한쪽이 수정(삽입, 삭제)되면 반대편에는 수동으로 갱신을 해줘야함
            user.addTodo(todo);
        });

        log.info("새로운 할 일이 저장되었습니다. 제목 : {}", dto.getTitle());

        return retrieve(email);
    }

    // 할 일 목록 불러오기
    public TodoListResponseDTO retrieve(String email) {
        //List<Todo> todoList = todoRepository.findAll();
        //List<Todo> allByUser = todoRepository.findAllByUser(userRepository.findByEmail(email).orElseThrow());

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("유저정보가 없습니다.")
        );

        List<Todo> todoList = user.getTodoList();

        // 엔터티 리스트를 dto리스트로 매핑하고 리턴
        List<TodoDetailResponseDTO> dtoList = todoList.stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());
        return TodoListResponseDTO.builder().todos(dtoList).build();
    }

    // 할 일 삭제
    public TodoListResponseDTO delete(String id, String email) {

        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("id가 존재하지 않아 삭제에 실패했습니다. - ID : {}, error : {}", id, e.getMessage());

            throw new RuntimeException("삭제에 실패했습니다.");
        }

        return retrieve(email);
    }

    // 할 일 체크 처리
    public TodoListResponseDTO check(TodoCheckRequestDTO dto, String email) {

        //Todo todo = todoRepository.findById(dto.getId())
        //        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
        //todo.setDone(dto.isDone());
        //todoRepository.save(todo);

        Optional<Todo> target = todoRepository.findById(dto.getId());

        target.ifPresent(todo -> {
            todo.setDone(dto.isDone());
            todoRepository.save(todo);
        });

        return retrieve(email);
    }
}

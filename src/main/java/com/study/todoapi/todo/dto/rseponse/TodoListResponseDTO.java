package com.study.todoapi.todo.dto.rseponse;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoListResponseDTO {

    private String error;   // 에러가 발생했을때 메세지를 저장
    private List<TodoDetailResponseDTO> todos;
}

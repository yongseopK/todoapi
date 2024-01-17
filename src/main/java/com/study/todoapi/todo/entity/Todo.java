package com.study.todoapi.todo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_todo")
public class Todo {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "todo_id")
    private String id;

    @Column(nullable = false, length = 30)
    private String title;

    private boolean done;

    @CreationTimestamp
    private LocalDateTime createDate;
}

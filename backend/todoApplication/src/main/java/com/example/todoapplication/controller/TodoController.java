package com.example.todoapplication.controller;

import com.example.todoapplication.dto.ResponseDTO;
import com.example.todoapplication.dto.TodoDTO;
import com.example.todoapplication.model.TodoEntity;
import com.example.todoapplication.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    final private TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = service.delete(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(temporaryUserId);

        List<TodoEntity> entities = service.update(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);

            // id를 null 로 초기화
            entity.setId(null);

            // 임시 사용자 아이디 설정
            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = service.create(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

}

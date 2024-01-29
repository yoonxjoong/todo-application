package com.example.todoapplication.service;

import com.example.todoapplication.model.TodoEntity;
import com.example.todoapplication.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    final private TodoRepository repository;

    public TodoService(TodoRepository repository){
        this.repository = repository;
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);

        try{
            repository.delete(entity);
        }catch (Exception e){
            log.error("error deleting entity " + entity.getId());
            throw new RuntimeException("error deleting entity "+ entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);

        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> create(final TodoEntity entity){
        validate(entity);

        repository.save(entity);

        return repository.findByUserId(entity.getUserId());
    }

    private void validate(TodoEntity entity) {
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}

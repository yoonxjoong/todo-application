package com.example.todoapplication.persistence;

import com.example.todoapplication.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    @Query("select t from TodoEntity t where t.userId = ?1")
    List<TodoEntity> findByUserId(String userId);
}

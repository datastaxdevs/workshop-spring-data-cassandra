package com.datastax.workshop.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ToDoService {

    @Autowired
    CrudRepository<Todo, UUID> crudRepository;

    public Stream<Todo> findAll() {
        return StreamSupport.stream(crudRepository.findAll().spliterator(), true);
    }

    public Optional<Todo> findByUUID(UUID uuid) {
        return crudRepository.findById(uuid);
    }

    public Todo create(Todo todo) {
        return crudRepository.save(todo);
    }

    public Todo update(Todo existingTodo, Todo todoReq) {
        if (null != todoReq.getTitle()) {
            existingTodo.setTitle(todoReq.getTitle());
        }
        if (existingTodo.getOrder() != todoReq.getOrder()) {
            existingTodo.setOrder(todoReq.getOrder());
        }
        if (existingTodo.isCompleted() != todoReq.isCompleted()) {
            existingTodo.setCompleted(todoReq.isCompleted());
        }
        return existingTodo;
    }

    public void deleteById(UUID uuid) {
        crudRepository.deleteById(uuid);
    }

    public void deleteAll() {
        crudRepository.deleteAll();
    }
}

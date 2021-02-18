package com.datastax.workshop.todo;

import com.datastax.oss.driver.shaded.guava.common.base.Functions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ToDoInMemoryRepository implements CrudRepository<Todo, UUID> {

    private Map<UUID, Todo> todoStore = new ConcurrentHashMap<>();

    @Override
    public Todo save(Todo todo) {
        todoStore.put(todo.getUuid(), todo);
        return todo;
    }

    @Override
    public <S extends Todo> Iterable<S> saveAll(Iterable<S> iterable) {
        todoStore.putAll(StreamSupport
                .stream(iterable.spliterator(), true)
                .collect(Collectors.toMap(Todo::getUuid, Functions.identity())));

        // ToDo IO: return correct data
        return null;
    }

    @Override
    public Optional<Todo> findById(UUID uuid) {
        return Optional.ofNullable(todoStore.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return todoStore.containsKey(uuid);
    }

    @Override
    public Iterable<Todo> findAll() {
        return todoStore.values();
    }

    @Override
    public Iterable<Todo> findAllById(Iterable<UUID> iterable) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public long count() {
        return todoStore.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        todoStore.remove(uuid);
    }

    @Override
    public void delete(Todo todo) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Todo> iterable) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteAll() {
        todoStore.clear();
    }
}

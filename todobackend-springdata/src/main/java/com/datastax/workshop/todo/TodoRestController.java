package com.datastax.workshop.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(
  methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
  maxAge = 3600,
  allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
  origins = "*" 
)
@RequestMapping("/api/v1/todos/")
public class TodoRestController {
    
    @Autowired
    ToDoService toDoService;
    
    @GetMapping
    public Stream<Todo> findAll(HttpServletRequest req) {
        return toDoService.findAll().map(t -> t.setUrl(req));
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
        Optional<Todo> todo = toDoService.findByUUID(UUID.fromString(uid));
        return todo
                .map(x -> ResponseEntity.ok(x.setUrl(req)))
                .orElse(ResponseEntity.notFound().build());
    }
     
    @PostMapping
    public ResponseEntity<Todo> create(HttpServletRequest req, @RequestBody Todo todoReq) throws URISyntaxException {
        Todo newTodo = new Todo(todoReq.getTitle(), todoReq.getOrder(), todoReq.isCompleted());
        newTodo.setUrl(req);
        Todo savedTodo = toDoService.create(newTodo);
        return ResponseEntity.created(new URI(savedTodo.getUrl())).body(savedTodo);
    }

    @PatchMapping("{uid}")
    public ResponseEntity<Todo> update(@PathVariable(value = "uid") String uid, @RequestBody Todo todoReq) {
        Optional<Todo> todo = toDoService.findByUUID(UUID.fromString(uid));
        return todo
                .map(existingTodo -> ResponseEntity.accepted().body(toDoService.update(existingTodo, todoReq)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
        Optional<Todo> todo = toDoService.findByUUID(UUID.fromString(uid));
        return todo
                .map(x -> {
                    toDoService.deleteById(x.getUuid());
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        toDoService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

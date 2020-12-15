# PART2

**Table of Content**
- **[1. Create a CRUD Controller](#1-create-a-crud-controller)**
- **[2. Test your Controller](#2-test-your-controller)**
- **[3. Sample Curl commands](#3-sample-curl-commands)**

---

## 1. Create a CRUD Controller

Checking the specification this is the ouput json should look like
Reminder: [https://todo-back-springboot220-java12.herokuapp.com/todos](https://todo-back-springboot220-java12.herokuapp.com/todos)

```json
{
  url: "http://localhost:8080/api/v1/todos/415fa735-d432-4dda-8558-db6cfddea03a",
  uuid: "415fa735-d432-4dda-8558-db6cfddea03a",
  title: "blah",
  completed: false,
  order: 95
}
```

✅ **Step 1a:** Create the following `Todo.java` file in a new package `todo`. The `URL` attribute is brought by the specification in an HateOAS approach, links to each resource is explicit

```java
package com.datastax.workshop.todo;
import java.util.UUID;
import lombok.Data;

@Data
public class Todo {
    private String  url;
    private UUID    uuid;
    private String  title;
    private boolean completed = false;
    private int     order = 0;
}
```

✅ **Step 1b:** Create the following `TodoRestController.java` file in same folder. Complete the following REST Controller uing an in-memory data structure of your choice to match the specification.

```java
package com.datastax.workshop.todo;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(
  methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
  maxAge = 3600,
  allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
  origins = "*" 
)
@RequestMapping("/api/v1/todos/")
public class TodoRestController {

 @GetMapping
 public Stream<Todo> findAll(HttpServletRequest req) {
   // list all todos 
   return null;
 }
    
 @GetMapping("/{uid}")
 public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
   // return a todo
   return null;
 }
     
 @PostMapping
 public ResponseEntity<Todo> create(HttpServletRequest req, @RequestBody Todo todoReq) throws URISyntaxException {
   // create a new todo
   return ResponseEntity.created(
        new URI("<build_url>"))
        .body(null);
 }
    
 @PatchMapping("{uid}")
 public ResponseEntity<Todo> update(
     HttpServletRequest req, 
     @PathVariable(value = "uid") String uid, 
     @RequestBody Todo todoReq) 
 throws URISyntaxException {
     // update an existing Task
     return ResponseEntity.accepted().body(null);
 }
    
 @DeleteMapping("{uid}")
 public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
   // if not found
   //return ResponseEntity.notFound().build();
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }

 @DeleteMapping
 public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
   // edit the structure
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }

}
```

You don't have to complete all in the 10min but try the first methods. Tips to get the URL of a current `HttpServletRequest` req, you can do:

```java
String reqUrl = req.getRequestURL().toString();
```

<details><summary><i>🖱️ Click me to show correction</i></summary><br/><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART3/todobackend-springdata/src/main/java/com/datastax/workshop/todo/Todo.java"><li>Todo.java</a><br/><a href=https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART3/todobackend-springdata/src/main/java/com/datastax/workshop/todo/TodoRestController.java"><li>TodoRestController.java</a><br/></details>

## 2. Test your Controller

✅ **Step 2a:** Run the test specifications to validate your implementation [https://www.todobackend.com/specs/index.html](https://www.todobackend.com/specs/index.html) providing the URL of your endpoint.


✅ **Step 2b:** Play with your implementation [https://www.todobackend.com/client/index.html](https://www.todobackend.com/client/index.html) providing the URL of your endpoint.

## 3. Sample Curl commands

You can access to the CRUD operations with the following CURL. You can use either the `CRUD` command line or [https://reqbin.com/curl](https://reqbin.com/curl).

- List all features with **findAll**

```
curl http://<your_endpoint>/api/v1/todos
```

- Delete all features with**deleteAll**

```bash
curl -X DELETE http://<your_endpoint>/api/v1/todos
```

- Read a Todo

```bash
curl http://<your_endpoint>/api/v1/todos/1eae2a0d-219c-4ff7-b9fd-3a460f399c49
```

- delete a Task

```bash
curl -X DELETE  http://<your_endpoint>/api/v1/todos/6cf009f9-f35e-46d6-9be2-ffe6f0dde4ba
```

- Create a Task

```bash
curl -X POST http://<your_endpoint>/api/v1/todos/ \
	 -H "Content-Type: application/json" \
     -d "{\"title\":\"Sample Task\"}"
```

--- 

Let's implements a real backend now

```bash
git checkout PART3
```
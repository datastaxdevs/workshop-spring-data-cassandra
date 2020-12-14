# PART2

**Table of Content**
- **[1. Create a CRUD Controller](#1-create-a-crud-controller)**
- **[2. Test your Controller](#2-test-your-controller)**
- **[3. Sample Curl commands](#3-sample-curl-commands)**

---

## 1. Create a CRUD Controller

Based on the screen we can design the following entity. The `URL` attribute is brought by the specification in an HateOAS approach, links to each resource is explicit

**Sample JSON output:**
```json
{
 url: "http://localhost:8080/api/v1/todos/415fa735-d432-4dda-8558-db6cfddea03a",
 uuid: "415fa735-d432-4dda-8558-db6cfddea03a",
 title: "blah",
 completed: false,
 order: 95
}
```

✅ **Step 1a:** Create the following Bean

```java
@Data
@AllArgsConstructor
public class Todo {
    private String  url;
    private UUID    uuid;
    private String  title;
    private boolean completed = false;
    private int     order = 0;
}
```

✅ **Step 1b:** Complete the following REST Controller uing an in-memory data structure of your choice to match the specification

```java
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
 }
    
 @GetMapping("/{uid}")
 public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid)
   // return a todo
 }
     
 @PostMapping
 public ResponseEntity<Todo> create(HttpServletRequest req, @RequestBody Todo todoReq) throws URISyntaxException {
   // create a new todo
   return new ResponseEntity.created(
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
git checkout -b PART3
```
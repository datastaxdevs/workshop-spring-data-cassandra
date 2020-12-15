# PART4

**Table of Content**
- **[1. Object Mapping](#1-object-mapping)**
- **[2. Create a Spring Data Repository](#2-create-a-spring-data-repository)**
- **[3. Unit Test of the repository](#3-unit-test-of-the-repository)**
- **[4. Plug Repository to the RestController](#4-plug-repository-to-the-restcontroller)**

---

## 1. Object Mapping

We can interact with Cassandra using the Cassandra Query Language (or CQL) but here we want some mapping between Object and Table.

In previous step we created an entity `TodoEntity` not dealing with the resource URL and defining the columns we needed.

Let's annotate the bean in a way we define more `PrimaryKey` and fields

```java
package com.datastax.workshop.todo;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = TodoEntity.TABLENAME)
public class TodoEntity {
    
    public static final String TABLENAME        = "todos";
    public static final String COLUMN_UID       = "uid";
    public static final String COLUMN_TITLE     = "title";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_ORDER     = "offset";
    
    @PrimaryKey
    @Column(COLUMN_UID)
    @CassandraType(type = Name.UUID)
    private UUID uid;
    
    @Column(COLUMN_TITLE)
    @CassandraType(type = Name.TEXT)
    private String title;
    
    @Column(COLUMN_COMPLETED)
    @CassandraType(type = Name.BOOLEAN)
    private boolean completed = false;
    
    @Column(COLUMN_ORDER)
    @CassandraType(type = Name.INT)
    private int order = 0;
    
    public TodoEntity(String title, int offset) {
        this(UUID.randomUUID(), title, false, offset);
    }
}
```    

## 2. Create a Spring Data Repository

There are 2 ways to create a repository. 

- You can either do 100% with an interface extending `CassandraRepository<E, K>`. Create a new file `CassandraRepository.java` in the todo folder. Use Gitpod to help you with the imports.

```java
@Repository
public interface TodoRepositoryCassandra 
  extends CassandraRepository<TodoEntity, UUID> {}
```

- You can go with the implementation `SimpleCassandraRepository<E, K>` which allows us to access the `CqlSession` and `CqlTemplate` to do custom queries later.

```java

// Beginning is omitted

@Repository
public class TodoRepositorySimpleCassandra 
  extends SimpleCassandraRepository<TodoEntity, UUID> {

    protected final CqlSession          cqlSession;
    protected final CassandraOperations cassandraTemplate;
    
    public TodoRepositorySimpleCassandra(CqlSession cqlSession, CassandraOperations ops) {
        super(
          new MappingCassandraEntityInformation<TodoEntity, UUID>(
            (CassandraPersistentEntity<TodoEntity>) 
             ops.getConverter()
                .getMappingContext()
                .getRequiredPersistentEntity(TodoEntity.class), 
             ops.getConverter()), ops);
        this.cqlSession = cqlSession;
        this.cassandraTemplate = ops;
    }

    // end is ommitted
```

## 3. Unit Test of the repository

We now want to leverage on Spring-boot to initiate the context for us and inject the implementation. We can sinply do: check that this unit test is passing.

```java
@SpringBootTest
class TodobackendSpringdataApplicationTests {

    @Autowired
    private TodoRepositoryCassandra repo;
    
	@Test
	void shoud_save_task_when_save() {
	    // Given
	    TodoEntity te = new TodoEntity("Sample task1", 1);
	    // When
	    repo.save(te);
	    // then
	    Assertions.assertTrue(repo.existsById(te.getUid()));
	}

}
```

Execute the test with the following command:
```bash
mvn test -Dtest=com.datastax.workshop.TodobackendSpringdataApplicationTests#shoud_save_task_when_save
```

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.95 s - in com.datastax.workshop.TodobackendSpringdataApplicationTests
14:22:39.125 WARN  org.springframework.beans.factory.support.DisposableBeanAdapter : Destroy method 'close' on bean with name 'cassandraDriverConfigLoader' threw an exception: java.util.concurrent.RejectedExecutionException: event executor terminated
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.817 s
[INFO] Finished at: 2020-12-15T14:22:39Z
[INFO] ------------------------------------------------------------------------
```

You can again access the `CQL Console` on astra and perform the CQL:

```sql
select * from todokeyspace.todos
```

![.](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/raw/PART4/images/astra-cqlconsole-select.png?raw=true)

## 4. Plug Repository to the RestController

Adapt Rest Controller to have the specs passing with the Cassandra Repository. Here are some snippet

```java
// Import, class definition
//[..]

 private TodoRepositoryCassandra repo;

 // You want to inject with the constructor
 public TodoRestController(TodoRepositoryCassandra todoRepo) {
   this.repo = todoRepo;
 }

@GetMapping("/{uid}")
public ResponseEntity<Todo> findById(
  HttpServletRequest req, 
  @PathVariable(value = "uid") String uid) {

  Optional<TodoEntity> e = repo.findById(UUID.fromString(uid));
  if (e.isEmpty()) {
    return ResponseEntity.notFound().build();
   }
   return ResponseEntity.ok(mapAsTodo(e.get())
                        .setUrl(req.getRequestURL().toString()));
}

// Your JOB...

```
<details><summary><i>🖱️ Click me to show correction</i></summary><br/><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART5/todobackend-springdata/src/main/java/com/datastax/workshop/todo/Todo.java"><li>Todo.java</a><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART5/todobackend-springdata/src/main/java/com/datastax/workshop/todo/TodoEntity.java"><li>TodoEntity.java</a><br/><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART5/todobackend-springdata/src/main/java/com/datastax/workshop/todo/TodoRestController.java"><li>TodoRestController.java</a><br/></details>


Your Application is now connected to Cassandra. Let's start it:

```
mvn clean spring-boot:run
```

Let's execute the unit test
```
gp preview https://www.todobackend.com/specs/index.html?$(gp url 8080)/api/v1/todos/
```

You can again check in Cassandra DB:

```sql
select * from todokeyspace.todos;
```

![.](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/raw/PART4/images/astra-cqlconsole-select-2.png?raw=true)

You have now a working application plug to Cassandra:
```
gp preview https://www.todobackend.com/client/index.html?$(gp url 8080)/api/v1/todos/
```

--- 
To have the working application. To see the solution move to PART5

```bash
git fetch --all
git reset --hard origin/PART5
git checkout PART5 
```

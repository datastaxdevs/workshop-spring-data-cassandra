
# PART 1

**Table of Content**
- **[1. Run Existing TodoApplication](#1-run-existing-todoapplication)**
- **[2. Create your Spring-Boot Application](#2-create-your-spring-boot-application)**
- **[3. Start your Spring-Boot Application](#3-start-your-spring-boot-application)**

---

For simplicity all exercise instructions are listed in a single `README.MD` document. As it is quite long we provide a table of contents after each chapter so you can navigate it easily.

## 1. Run Existing TodoApplication

**TodoMVC** is a fabulous community contribution that helps developers compare frameworks on the basis of actual project code, not just claims and anecdotes. TodoMVC is an immensely valuable attempt at a difficult problem - providing a structured way of comparing JS libraries and frameworks.

![TodoMVC](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todomvc.png?raw=true)

**✅ Step 1a. Visit [TodoMVC.com](http://todomvc.com/) to discover what we are building today** : We want you to see what it looks like before starting anything. So please browse to [http://todomvc.com/](http://todomvc.com/) and take a look. You will see that there are multiple implementations, feel free to pick one of those and test it live in your browser.


**[Todo-Backend](https://www.todobackend.com/)** is an example to showcase backend tech stacks. The Todo-Backend project defines a simple web API spec for managing a todo list. Contributors implement that spec using various tech stacks. Those implementations are cataloged below. A spec runner verifies that each contribution implements the exact same API, by running an automated test suite which defines the API.

![TodoBackend](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend.png?raw=true)

There are multiple free implementations available. In this workshop we will use: [https://todo-backend-webapi.azurewebsites.net/todo](https://todo-backend-webapi.azurewebsites.net/todo)

**✅ Step 1b. Test TodoBackEnd Spec Runner** : Use the [spec runner](https://www.todobackend.com/specs/index.html) with the `endpoint` listed above to evaluate if API matches the requirements. To do so copy-paste one URL and click `Run Tests`.

![TodoBackendTest](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-runtest.png?raw=true)

- Link to [`SpecRunner`](https://www.todobackend.com/specs/index.html?https://dm-tdb-02.azurewebsites.net/todo)
https://todobackend.com/specs/index.html?https://todo-backend-webapi.azurewebsites.net/todo

```
gp preview https://todobackend.com/specs/index.html?https://todo-backend-webapi.azurewebsites.net/todo
```

**👁️ Expected output**

![TodoBackendOuput](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-output-host.png?raw=true)

**Note:** *During the live stream there will be dozens of people testing the same endpoints.  It is possible that we could hit some slowness or issues with race conditions.*

**✅ Step 1c. Test TodoBackEnd Web Client** : todoBackend.com provides a [client](https://www.todobackend.com/client/index.html) to work with the API. As before pick  the `endpoint` we used before and try the client.

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-runclient.png?raw=true)

- Link to [`WebClient`](https://www.todobackend.com/client/index.html?https://dm-tdb-02.azurewebsites.net/todo)

**👁️ Expected output**

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-output-client.png?raw=true)

Our mission in the next hour is to implement the *backend API* and store data into Apache Cassandra™. We have you covered by providing the skeleton of the application.

## 2. Create your Spring-Boot Application

**✅ Step 2a. How to create an application Skeleton** 

[Start.spring.io](http://start.spring.io) routes you to the Spring Initializr which helps you create projects from scratch.

Fill the different fields as shown in the picture below. As we are lazy, click [THIS LINK](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.4.1.RELEASE&packaging=jar&jvmVersion=11&groupId=com.datastax.workshop&artifactId=todobackend-springdata&name=todobackend-springdata&description=TodoBackend%20Spring%20Boot%20Microservices%20with%20Spring%20Data%20for%20Apache%20Cassandra&packageName=com.datastax.workshop&dependencies=data-cassandra,devtools,web,lombok,actuator)
 to have everything populated already.

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/spring-initializr.png?raw=true)

Now you could simply click the **GENERATE** button to download the ZIP but the repository for this workshop (branch PART1) contains exactly the same content to avoid you having to import all this.

## 3. Start your Spring-Boot Application 

**✅ Step 3a. (try to) Start your application** : Make sure you are in the proper folder and use this maven command to start the application.

```bash
cd /workspace/workshop-spring-data-cassandra/todobackend-springdata

mvn clean install spring-boot:run
```

For those who read the text:

```diff
- You will get an ERROR, THIS IS EXPECTED
```

**👁️ Expected output**
```
Caused by: com.datastax.oss.driver.api.core.AllNodesFailedException: Could not reach any contact point, make sure you've provided valid addresses (showing first 1 nodes, use getAllErrors() for more): Node(endPoint=/127.0.0.1:9042, hostId=null, hashCode=17e0c2f0): [com.datastax.oss.driver.api.core.connection.ConnectionInitException: [s0|control|connecting...] Protocol initialization request, step 1 (OPTIONS): failed to send request (io.netty.channel.StacklessClosedChannelException)]
```

**✅ Step 3b. Fix your application**

With dependencies introduced by Spring Data Cassandra, Spring Boot wants to create beans to connect to Cassandra but, as of now we don't have any backend. We will fix it soon but for now let's add a couple of lines in the main class.  In order to remove  **autoconfigurations**  we use exclude clauses in the annotation `SpringBootApplication` for class `TodobackendSpringdataApplication`.

Edit `SpringBootApplication` annotation in class `TodobackendSpringdataApplication`:

- Add the following **exclude** statements. 

```java
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;


@SpringBootApplication(exclude = { 
    CassandraDataAutoConfiguration.class, 
    CassandraAutoConfiguration.class 
})
```
You should now be able to start the application.

```bash
mvn clean install spring-boot:run
```

**👁️ Expected output**

```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.1)

2020-12-13 17:32:10.471  INFO 6029 --- [  restartedMain] c.d.w.TodobackendSpringdataApplication   : Starting TodobackendSpringdataApplication using Java 11.0.9 on ws-543d6783-68dd-4bcc-b2da-cd1466bb71b4 with PID 6029 (/workspace/workshop-spring-data-cassandra/todobackend-springdata/target/classes started by gitpod in /workspace/workshop-spring-data-cassandra/todobackend-springdata)
2020-12-13 17:32:10.473  INFO 6029 --- [  restartedMain] c.d.w.TodobackendSpringdataApplication   : No active profile set, falling back to default profiles: default
2020-12-13 17:32:10.515  INFO 6029 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2020-12-13 17:32:10.515  INFO 6029 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2020-12-13 17:32:11.112  INFO 6029 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Reactive Cassandra repositories in DEFAULT mode.
2020-12-13 17:32:11.120  INFO 6029 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 4 ms. Found 0 Reactive Cassandra repository interfaces.
2020-12-13 17:32:11.123  INFO 6029 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Cassandra repositories in DEFAULT mode.
2020-12-13 17:32:11.124  INFO 6029 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 0 ms. Found 0 Cassandra repository interfaces.
2020-12-13 17:32:11.471  INFO 6029 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-12-13 17:32:11.480  INFO 6029 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-12-13 17:32:11.480  INFO 6029 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
2020-12-13 17:32:11.536  INFO 6029 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-12-13 17:32:11.536  INFO 6029 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1020 ms
2020-12-13 17:32:11.795  INFO 6029 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-12-13 17:32:11.970  INFO 6029 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2020-12-13 17:32:11.975  INFO 6029 --- [  restartedMain] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 2 endpoint(s) beneath base path '/actuator'
2020-12-13 17:32:12.006  INFO 6029 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-12-13 17:32:12.020  INFO 6029 --- [  restartedMain] c.d.w.TodobackendSpringdataApplication   : Started TodobackendSpringdataApplication in 1.862 seconds (JVM running for 2.264)
```

If the preview window does not automatically refresh, open a new terminal (`Terminal > New Terminal` in menu) and use the following command:

```
gp preview $(gp url 8080)
```

**👁️ Expected output**

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART1/images/gitpod-empty.png?raw=true)


**✅ Step 3c. Create your hello world endpoint**

Pretty sad to have an error page becaise we do not have any controller, let's create one `HelloWorldRestController` in a new package `hello`:

```java
package com.datastax.workshop.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {
    @RequestMapping("/")
    public String hello() {
        return "Hello World !";
    }
}
```

Stop with CRTL+C and restart with `mvn clean install spring-boot:run`

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART1/images/gitpod-hello.png?raw=true)

Much better. Stop the application with CRTL+C

This is it for part 1. 

To move to part 2 checkout branch 
`STEP2`. 

**Note**
> Moving to PART2 You will loose your local changes as we enforce with `-f`. As an alternative you can also fork the repository, work with your copy, then you would be able to commit before moving to part2.

```bash
git fetch --all
git reset --hard origin/PART2
git checkout PART2 
```

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/welldone.jpg?raw=true)

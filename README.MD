
# Hands-On Spring Data Cassandra

![SplashScreen](./images/splash.jpeg?raw=true)

[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra) 
[![License Apache2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Discord](https://img.shields.io/discord/685554030159593522)](https://discord.com/widget?id=685554030159593522&theme=dark)


Today we are creating the Todo Application with storage in Apache Cassandra™.

**Table of Content**
- **[1. Run Existing TodoApplication](#)**
- **[2. Create your Spring-Boot Application](#)**
- **[3. Start your Spring-Boot Application](#)**
- **[4. Implement the API](#)**

---

For simplicity all exercise instructions are listed in a single `README.MD` document. As it is quite long we provide a table of contents after each chapter so you can go back to it.

## 1. Run Existing TodoApplication

**TodoMVC** is a fabulous community contribution that helps developers compare frameworks on the basis of actual project code, not just claims and anecdotes. TodoMVC is an immensely valuable attempt at a difficult problem - providing a structured way of comparing JS libraries and frameworks.

![TodoMVC](https://github.com/DataStax-Academy/microservices-java-workshop-online/blob/master/z-materials/images/todomvc.png?raw=true)

**✅ Step 1a. Visit [TodoMVC.com](http://todomvc.com/) to discover what we are building today** : We want you to see what it look like before starting anything. So simply access the URL [http://todomvc.com/](http://todomvc.com/) and browse. You will see that there are multiple implementations, pick one of those and test it live in your browser.


**Todo-Backend** is a shared example to showcase backend tech stacks. The Todo-Backend project defines a simple web API spec - for managing a todo list. Contributors implement that spec using various tech stacks. Those implementations are cataloged below. A spec runner verifies that each contribution implements the exact same API, by running an automated test suite which defines the API.

![TodoBackend](./images/todobackend.png?raw=true)

There are multiple free implementations available. Some implementations provide endpoints to be tested in `heroku`. [Heroku provide free hosting for a Java application but this is on-demand](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku). With `Heroku` The application will be sleeping until you access the endpoint. So it will take 30s for the first person to have the application running. Among the available free endpoints we can list:
- `Endpoint#1` : [https://todo-back-springboot220-java12.herokuapp.com/todos](https://todo-back-springboot220-java12.herokuapp.com/todos)
- `Endpoint#2` : [https://todo-quarkus.herokuapp.com/todos](https://todo-quarkus.herokuapp.com/todos)
- `Endpoint#3` : [https://todo-backend-micronaut.herokuapp.com/todos](https://todo-backend-micronaut.herokuapp.com/todos)

**✅ Step 2b. Test TodoBackEnd Spec Runner** : Locate the [spec runner](https://www.todobackend.com/specs/index.html) and use one of the `endpoints` listed above to evaluate if API matchs the requirements. To do so copy-paste one URL and click `Run Tests`.

![TodoBackendTest](./images/todobackend-runtest.png?raw=true)

- `SpecRunner#1` : [https://www.todobackend.com/specs/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos](https://www.todobackend.com/specs/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos)
- `SpecRunner#2` : [https://www.todobackend.com/specs/index.html?https://todo-quarkus.herokuapp.com/todos](https://www.todobackend.com/specs/index.html?https://todo-quarkus.herokuapp.com/todos)
- `SpecRunner#3` : [https://www.todobackend.com/specs/index.html?https://todo-backend-micronaut.herokuapp.com/todos](https://www.todobackend.com/specs/index.html?https://todo-backend-micronaut.herokuapp.com/todos)

**👁️ Expected output**

![TodoBackendOuput](./images/todobackend-output-host.png?raw=true)

**Note:** *During the live stream we will be dozens of people testing the same endpoints - maybe - we could hit some slowness or issues with race conditions.*

**✅ Step 2c. Test TodoBackEnd Web Client** : todoBackend.com provides a [client](https://www.todobackend.com/client/index.html) to work with the API. As before pick one of the `endpoints` listed before and try the client.

![TodoBackendClient]./images/todobackend-runclient.png?raw=true)


- `WebClient#1` : [https://www.todobackend.com/client/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos](https://www.todobackend.com/client/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos)
- `WebClient#2` : [https://www.todobackend.com/client/index.html?https://todo-quarkus.herokuapp.com/todos](https://www.todobackend.com/client/index.html?https://todo-quarkus.herokuapp.com/todos)
- `WebClient#3` : [https://www.todobackend.com/client/index.html?https://todo-backend-micronaut.herokuapp.com/todos](https://www.todobackend.com/client/index.html?https://todo-backend-micronaut.herokuapp.com/todos)

**👁️ Expected output**

![TodoBackendClient](./images/todobackend-output-client.png?raw=true)

Our mission withing the next hour is to implement the *backend API* and store  data into Apache Cassandra™. We have you covered by providing the skeleton of the application.

## 2. Create your Spring-Boot Application

**✅ How to create a Skeleton** 

[Start.spring.io](start.spring.io) routes you to the Spring Initializr which helps you create projects from scratch.

Let's fill what we need for today as the picture. 

But we are lazy and click [THIS LINK](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.4.1.RELEASE&packaging=jar&jvmVersion=11&groupId=com.datastax.workshop&artifactId=todobackend-springdata&name=todobackend-springdata&description=TodoBackend%20Spring%20Boot%20Microservices%20with%20Spring%20Data%20for%20Apache%20Cassandra&packageName=com.datastax.workshop&dependencies=data-cassandra,devtools,web,lombok,actuator)
 to have everything populated already.

![TodoBackendClient](./images/spring-initializr.png?raw=true)

Now simply click button **GENERATE** to download the ZIP if you want.

Guess what ? this is the initial content of this repository (MASTER) so you have nothing to do here, by moving from branches to branches we will build the full application.

## 3. Start your Spring-Boot Application 

**✅ Open gitpod** : [Gitpod](https://www.gitpod.io/) is an IDE 100% online based on Eclipse Theia. To initialize your environment simply click on the button below *(CTRL + Click to open in new tab)*

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra)

Target url looks like  `https://<your_uid>.<your_region>.gitpod.io/#/workspace/workshop-spring-data-cassandra`. These URL's are dynamic and we cannot provide clickable links in advance. You will need to copy-paste `<your_uid>.<your_region>` as we will insert them in each URL during the exercises.

```
⚠️ By default Autosaved is not enabled in Gitpod. Don't forget to save your modifications with CTRL+S
```

**👁️ Expected output**

![SplashScreen](https://github.com/DataStax-Academy/microservices-java-workshop-online/blob/master/z-materials/images/gitpod-home.png?raw=true)

**That's it.** Gitpod provides all tools we will need today including `Maven` and exporting port `8080`. At initialization of the workspace we schedule a `mvn clean install` to download dependencies. You can still download the repository and bookmark it as all materials including slides are included.

💻 There is nothing preventing it for running the project on your laptop you need `Maven` and an `IDE` like Spring STS but you are on yout own.

```java
// Disable 
@SpringBootApplication(exclude = { CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class })
```

```java
package com.datastax.workshop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @RequestMapping("/")
    public String hello() {
        return "Hello World !";
    }
}
```


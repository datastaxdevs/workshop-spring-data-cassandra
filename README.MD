
# PART2 - Hands-On Spring Data Cassandra

![SplashScreen](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/splash.jpeg?raw=true)

[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra) 
[![License Apache2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Discord](https://img.shields.io/discord/685554030159593522)](https://discord.com/widget?id=685554030159593522&theme=dark)


Today we are creating the Todo Application with storage in Apache Cassandra™.

**Table of Content**
- **[1. Create InMemory Repository](#)**
- **[2. Run Specifications](#)**
- **[3. Run Application with Client](#)**

---

## 1. Create InMemory Repository

Our Object
```java
@Data
@AllArgsConstructor
public class Todo {
    private String url;
    private UUID uuid;
    private String title;
    private boolean completed = false;
    private int order = 0;
}
```

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/welldone.jpg?raw=true)


# Hands-On Spring Data Cassandra

![SplashScreen](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/splash.jpeg?raw=true)

[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra) 
[![License Apache2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Discord](https://img.shields.io/discord/685554030159593522)](https://discord.com/widget?id=685554030159593522&theme=dark)


Today we are creating the Todo Application with storage in Apache Cassandra™.

## 1. Bootstrapping 

[Gitpod](https://www.gitpod.io/) is an IDE 100% online based on Eclipse Theia. To initialize your environment simply click on the button below *(CTRL + Click to open in new tab)* You will be asked for you github account.

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra)

Target url looks like  `https://<your_uid>.<your_region>.gitpod.io/#/workspace/workshop-spring-data-cassandra`. These URL's are dynamic and we cannot provide clickable links in advance. You will need to copy-paste `<your_uid>.<your_region>` as we will insert them in each URL during the exercises.

**👁️ Expected output**

*The screenshot may be slightly different based on your default skin and a few edits in the read me.*

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/gitpod-home.png?raw=true)

**That's it.** Gitpod provides all tools we will need today including `Maven` and exporting port `8080`. At initialization of the workspace we schedule a `mvn clean install` to download dependencies. You can still download the repository and bookmark it as all materials including slides are included.

```
💻 There is nothing preventing you it for running the project on your laptop you need `Maven` and an `IDE` like Spring STS but you are on yout own.
```

Let's get starting:
```bash
git checkout PART1
```

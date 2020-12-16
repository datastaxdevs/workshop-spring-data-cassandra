
# Hands-On Spring Data Cassandra


[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra) 
[![License Apache2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Discord](https://img.shields.io/discord/685554030159593522)](https://discord.com/widget?id=685554030159593522&theme=dark)

Today we will develop the famous TodoApplication backend with a storage in **Apache Cassandra™** with **Spring Boot** and **Spring Data**

![SplashScreen](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/splash.jpeg?raw=true)


## 1. Launch Gitpod

[Gitpod](https://www.gitpod.io/) is an IDE 100% online based on Eclipse Theia. To initialize your environment simply click on the button below *(CTRL + Click to open in new tab)* You will be asked for you github account.

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/DataStax-Academy/workshop-spring-data-cassandra)

**👁️ Expected output**

*The screenshot may be slightly different based on your default skin and a few edits in the read me.*

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/gitpod-home.png?raw=true)

You can be asked to import the project, please accept to have java features enabled for you project.

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/import.png?raw=true)

**That's it.** Gitpod provides all tools we will need today including `Maven` and exporting port `8080`. At initialization of the workspace we schedule a `mvn clean install` to download dependencies.

Also you may have noticed there is a build happening - even before we get started. The sample project already exists and loading the developer enviroment triggers a build to download all the maven dependencies so you don't have to.

## 2. Know your gitpod

The workshop application has opened with an ephemeral URL. To know the URL where your application endpoint will be exposed you can run the following command in the terminal:

```bash
gp url 8080
```

**👁️ Expected output**

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/gitpod-url.png?raw=true)


### Working on your laptop
> 💻 There is nothing preventing you from running the workshop on your own laptop. If you do so you will need Maven, an IDE like Spring STS, and curl. You will have to adapt the commands and the path based on your environment and install the dependencies yourself.


🚀 **Let's get starting**

To move to branch `PART1`, in a terminal use the following command. 

- You should read the instructions in gitpod now as moving to the next branch will update this README with the new instructions.

- When you move from one branch to another using checkout you will have the workspace populated with the solution. Your local changes will be lost.


```bash
git checkout PART1
```


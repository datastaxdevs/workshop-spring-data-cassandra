# PART5

To expose the REST API from the repository we could have used Spring Data REST but here the specification was quite precise.

To avoid using 2 beans `Todo` and `TodoEntity` with one only adding an URL we could have used Spring HateOAS and used `Resource`.

To test instead of simple CURL we want to generate a small web client. Don't worry there is no work on you side as we will do it for you.

We are using a community project [SpringDoc](https://springdoc.org/).  OpenAPI is an evolution of Swagger2 and this is why it started at version 3.

First we need a a new dependency in the `pom.xml`
```xml
	<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-ui</artifactId>
	  <version>1.5.1</version>
	</dependency>
``` 

..and a couple of new keys in `application.properties`
```properties
# Spring Documentation
springdoc.api-docs.enabled=true
springdoc.api-docs.groups.enabled=true
springdoc.swagger-ui.display-request-duration=true
springdoc.swagger-ui.groups-order=DESC
springdoc.group-configs[0].group=Rest Controllers (Spring MVC)
springdoc.group-configs[0].packages-to-scan=com.datastax.workshop
```

We create a bean to set Spring-doc configuration. It will provide the meta data. We also need to set the MVC configurer to allow our static files:

```java
package com.datastax.workshop.conf;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiDocumentationConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openApiSpec() {
        String des = "Implementation of TodoBackend application with Spring WebMVC and storage in Apache Cassandra";
        Info info  = new Info().title("DevWorkshop :: TodoBackend Rest API")
                .version("1.0").description(des)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                .url("http://springdoc.org"));
        return new OpenAPI().addServersItem(new Server().url("/")).info(info);
    }
    
    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("Monitoring (Actuator)")
                .pathsToMatch("/actuator/**")
                .pathsToExclude("/actuator/health/*")
                .build();
    }
    
    /**
     * By overriding `addResourceHandlers` from {@link WebMvcConfigurer}, 
     * we tell SpringMVC not to use Thymeleaf to access Swagger UI
     * 
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
```

We should also change `HelloWorld` in a way that it can route to the documentation when we start the application:

```java
package com.datastax.workshop.hello;

import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldRestController {
    
    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;
    
    @GetMapping(DEFAULT_PATH_SEPARATOR)
    public String index() {
        return REDIRECT_URL_PREFIX + swaggerUiPath;
    }
}
```

Finally, and more importantly, you define annotations in your controller

**BEFORE**
```java
@PostMapping
public ResponseEntity<Todo> create(
  HttpServletRequest req, 
  @RequestBody Todo todoReq) 
throws URISyntaxException {
 TodoEntity te = mapAsTodoEntity(todoReq);
 repo.save(te);
 todoReq.setUuid(te.getUid());
 todoReq.setUrl(req);
 return ResponseEntity.created(new URI(todoReq.getUrl())).body(todoReq);
}
```

**AFTER**

```java
/**
 * CREATE = Create a new Todo (POST)
*/
@PostMapping
@Operation(summary = "Create a new Todo", 
 description = "POST is the proper http verb when you cannot provide the full URL (including id)", 
 tags = { "create" })
@ApiResponses(value = {
  @ApiResponse(responseCode = "201", 
               description = "The task has been successfully created",
               content = @Content(schema = @Schema(implementation = Todo.class))),
  @ApiResponse(responseCode = "400", description = "Title is blank but is mandatory"),
  @ApiResponse(responseCode = "500", description = "An error occur in storage") })
public ResponseEntity<Todo> create(HttpServletRequest req,
 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update all fields if needed", 
                    required = true, 
                    content = @Content(schema = @Schema(implementation = Todo.class))) 
            @RequestBody Todo todoReq) 
throws URISyntaxException {
  TodoEntity te = mapAsTodoEntity(todoReq);
  repo.save(te);
  todoReq.setUuid(te.getUid());
  todoReq.setUrl(req);
  return ResponseEntity.created(new URI(todoReq.getUrl())).body(todoReq);
}
```

There is more code in the annotation that the real code, really.

![.](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/raw/PART5/images/openapi.png?raw=true)

To see all the class and the final product simply move to branch `ZEND`

```bash
git fetch --all
git reset --hard origin/ZEND
git checkout ZEND 
```

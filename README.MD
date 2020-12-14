# PART5

To expose the REST API from the repository we could have use Spring Data REST but here the specification was quite precise.

To avoid using 2 beans `Todo` and `TodoEntity` with one only adding an URL we could have use Spring HateOAS and use `Resource`.

To test instead of simple CURL we want to generate a small web client. No work on you side here we will do it for you.

We are using a community project [SpringDoc](https://springdoc.org/). The OpenAPI is kind of Swagger2.

```xml
	<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-ui</artifactId>
	  <version>${springdoc.version}</version>
	</dependency>

	<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-data-rest</artifactId>
	  <version>${springdoc.version}</version>
  </dependency>
``` 

We then create a bean :

```java
@Configuration
public class OpenApiDocumentationConfig {

    @Bean
    public OpenAPI openApiSpec(@Value("${springdoc.version}") String appVersion) {
        String des = "Implementation of TodoBackend application with Spring WebMVC and storage in Apache Cassandra";
        Info info  = new Info().title("DevWorkshop :: TodoBackend Rest API")
                .version(appVersion).description(des)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                .url("http://springdoc.org"));
        return new OpenAPI().addServersItem(new Server().url("/")).info(info);
    }
    
    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder().setGroup("Monitoring (Actuator)")
                .pathsToMatch("/actuator/**")
                .pathsToExclude("/actuator/health/*")
                .build();
    }
    
}
```

And now annotated the controller
```java
@Operation(
  summary = "Retrieve the complete list of Taskss", 
  description = "List all records in the tables", 
  tags = { "todos" })
@ApiResponses(value = {
  @ApiResponse(
    responseCode = "200",
    description = "The list has been retrieved even if empty no error", 
    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResource.class)))) })  
@GetMapping
public Stream<Todo> findAll(HttpServletRequest req) {
  return repo.findAll().stream()
             .map(TodoRestController::mapAsTodo)
             .map(t -> t.setUrl(req));
}
```

We can change from hello World to redirection to the WEBUI.
```java
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

@Controller
public class HelloWorldController {
    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;
    @GetMapping(DEFAULT_PATH_SEPARATOR)
    public String index() {
        return REDIRECT_URL_PREFIX + swaggerUiPath;
    }
}
```

--- 

To have the final product simply move to FULL branch

```bash
git checkout ZEND
```

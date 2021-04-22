# PART3

**Table of Content**
- **[1. Create your Astra Instance](#1-create-your-astra-instance)**
- **[2. Connectivity to Cassandra](#2-connectivity-to-cassandra)**
- **[3. Connect the application to ASTRA](#3-connect-the-application-to-astra)**

---

## 1. Create your Astra instance

**`ASTRA`** is the simplest way to run Cassandra with zero operations at all - just push the button and get your cluster. No credit card required, $25.00 USD credit every month, roughly 5M writes, 30M reads, 40GB storage monthly - sufficient to run small production workloads.  

**✅ Step 1a: Register (if needed) and Sign In to Astra**

 [https://astra.datastax.com](https://dtsx.io/workshop): You can use your `Github`, `Google` accounts or register with an `email`.

_Make sure to chose a password with minimum 8 characters, containing upper and lowercase letters, at least one number and special character_

**✅ Step 1b:Create a "pay as you go" plan**

Follow this [guide](https://docs.datastax.com/en/astra/docs/creating-your-astra-database.html), to set up a pay as you go database with a free $25 monthly credit.

- **Select the pay as you go option**: Includes $25 monthly credit - no credit card needed to set up.

You will find below which values to enter for each field.

- **For the database name** - `myfreetierdb.` While Astra allows you to fill in these fields with values of your own choosing, please follow our recommendations to ensure the application runs properly.

- **For the keyspace name** - `todokeyspace`. It's really important that you use the name "free" for the code to work.

_You can technically use whatever you want and update the code to reflect the keyspace. This is really to get you on a happy path for the first run._

- **For provider and region**: Choose and provider (either GCP or AWS). Region is where your database will reside physically (choose one close to you or your users).

- **Create the database**. Review all the fields to make sure they are as shown, and click the `Create Database` button.

You will see your new database `pending` in the Dashboard.

![my-pic](https://github.com/datastaxdevs/shared-assets/blob/master/astra/dashboard-pending-1000-update.png?raw=true)

The status will change to `Active` when the database is ready, this will only take 2-3 minutes. You will also receive an email when it is ready.

## 2. Connectivity to Cassandra

**✅ 2a. Generate your application token**

If you don't already have one follow the instructions [**HERE**](https://docs.datastax.com/en/astra/docs/manage-application-tokens.html#_create_application_token) to generate your new token. **Don't forget to download it once created because you will not be able to see it again** without generating a new one.

Once you **DOWNLOAD** the token if you view the contents they should look something like this:

```shell
"Client Id","Client Secret","Token","Role"
"fdsfdslKFdLFdslDFFDjf","aaaaaaadsdadasdasdasdfsadfldsjfldjdsaldjasljdasljdsaljdasljdasljdlasjdal-FLflirFdfl.lfjdfdsljfjdl+fdlffkdsslfd","AstraCS:ppppdspfdsdslfjsdlfjdlj:540524888-04384039399999999999999999","Admin User"
```

You'll need to use this in a moment clientId is your username and clientSecret your passwoord **keep it handy**.
a
With our `Cassandra-as-a-service` instance ready let's connect from our application.

**✅ Step 2b. Get the secure connect bundle**

Go to the Astra home, select your database name on the right and find the `connect` tab

Once on the **Connect** screen pick `Java` on the left pannel and locate the `Secure Connect Bundle` link. 

![TodoBackend](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART3/images/download-secure.png?raw=true)

Open the button with the arrow, then copy the link with a right click

![TodoBackend](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART3/images/copy-link.png?raw=true)

In gitpod, execute the following command and substitute the link you copied in the previous step. Becareful about the double quotes as they are mandatory. We are downloading the ZIP **outside the repository** this way when we move from one branch to another we don't loose it.

```bash
curl -L "<Your_link>" > /tmp/creds.zip 
```

Now check the size of  `creds.zip` file, it should be about 12KB. If the file is too small do the operation again but refresh astra page first. It means your link has timeout.

```
ls -l /tmp/creds.zip 
```

**👁️ Expected output**

```bash
gitpod /workspace/workshop-spring-data-cassandra $ ls -l /tmp/creds.zip 
-rw-r--r-- 1 gitpod gitpod 12374 Dec 15 13:42 /tmp/creds.zip
```

**✅ Step 2b. Fix unit test `Ex1_ConnectivityToAstraExplicitTest.java`**

Unit Tests are located in `src/test/java`. 
Open the file `Ex1_ConnectivityToAstraExplicitTest` 

Open file `src/main/resources/application.properties` and edit the keys with values of your DB, if you used the values provided you don't have to change anything.

```properties
spring.data.cassandra.keyspace-name=todokeyspace
spring.data.cassandra.username=<clientID>
spring.data.cassandra.password=<clientSecret>
```

You can now run the UNIT TEST with your IDE or Maven in a terminal (easier with gitpod 
```bash
mvn test -Dtest=com.datastax.workshop.Ex1_ConnectivityToAstraExplicitTest#should_connect_to_Astra
```

**👁️ Expected output**

```bash
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.datastax.workshop.Ex1_ConnectivityToAstraExplicitTest
13:46:47.920 INFO  com.datastax.workshop.Ex1_ConnectivityToAstraExplicitTest :  + [OK] - Connection Established to Astra with Keyspace todokeyspace
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.908 s - in com.datastax.workshop.Ex1_ConnectivityToAstraExplicitTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.591 s
[INFO] Finished at: 2020-12-15T13:46:50Z
[INFO] ------------------------------------------------------------------------
```

**✅ Step 2c. Run unit test `Ex2_CreateSchemaInAstraTest.java`**

Open exercice 2 and see the code to connect and to create a new table if it does not exist.

```bash
mvn test -Dtest=com.datastax.workshop.Ex2_CreateSchemaInAstraTest#should_create_expected_table
```

**👁️ Expected output**

```bash
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.datastax.workshop.Ex2_CreateSchemaInAstraTest
13:47:35.022 INFO  com.datastax.workshop.Ex2_CreateSchemaInAstraTest : Connection Established to Astra with Keyspace 'todokeyspace'
13:47:35.061 INFO  com.datastax.workshop.Ex2_CreateSchemaInAstraTest : Table 'todos' has been created (if needed).
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.015 s - in com.datastax.workshop.Ex2_CreateSchemaInAstraTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.373 s
[INFO] Finished at: 2020-12-15T13:47:37Z
[INFO] ------------------------------------------------------------------------
```

You can now go to the CQLConsole in ASTRA and describe the keyspace, our new table should be there

![.](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/raw/PART3/images/cqlconsole.png?raw=true)

```sql
token@cqlsh> describe keyspace todokeyspace;
```

The output should look like this
```

CREATE KEYSPACE todokeyspace WITH replication = {'class': 'NetworkTopologyStrategy', 'dc-1': '1'}  AND durable_writes = true;

CREATE TABLE todokeyspace.todos (
    uid uuid PRIMARY KEY,
    completed boolean,
    offset int,
    title text
) WITH additional_write_policy = '99PERCENTILE'
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair = 'BLOCKING'
    AND speculative_retry = '99PERCENTILE';

token@cqlsh> 
```

## 3. Connect the application to ASTRA

With the file setup we can now provide a Cassandra connection in all beans of the application. By convention `spring-data` will read the different keys we provided.

To enable the connectivity to ASTRA we need to create the following file `SpringDataConfig.java` in the folder `conf`.

```java
package com.datastax.workshop.conf;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDataConfig {
    
    @Value("${datastax.astra.secure-connect-bundle}")
    private File cloudSecureBundle;
    
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        return builder -> 
          builder.withCloudSecureConnectBundle(cloudSecureBundle.toPath());
    }
    
}
```

You can now remove the excludes we added before in class `TodobackendSpringdataApplication`

```java

//@SpringBootApplication(exclude = { CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class })
@SpringBootApplication
public class TodobackendSpringdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodobackendSpringdataApplication.class, args);
	}

}
```

You can now re-execute the following test

```bash
mvn test -Dtest=com.datastax.workshop.TodobackendSpringdataApplicationTests#contextLoads
```

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.103 s - in com.datastax.workshop.TodobackendSpringdataApplicationTests
14:03:36.924 WARN  org.springframework.beans.factory.support.DisposableBeanAdapter : Destroy method 'close' on bean with name 'cassandraDriverConfigLoader' threw an exception: java.util.concurrent.RejectedExecutionException: event executor terminated
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.266 s
[INFO] Finished at: 2020-12-15T14:03:37Z
[INFO] ------------------------------------------------------------------------
```

The application should now work as well.

```bash
mvn spring-boot:run
```

<details><summary><i>🖱️ Click me to show correction</i></summary><br/><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART4/todobackend-springdata/src/main/java/com/datastax/workshop/conf/SpringDataConfig.java"><li>SpringDataConfig.java</a><br/><a href="https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/PART4/todobackend-springdata/src/main/java/com/datastax/workshop/TodobackendSpringdataApplication.java"><li>TodobackendSpringdataApplication.java</a><br/></details>
<br/>

Open the preview with, your app is now connected to Cassandra
```
gp preview $(gp url 8080)/
```

--- 
Let's implement the repository in part4

```bash
git fetch --all
git reset --hard origin/PART4
git checkout PART4 
```

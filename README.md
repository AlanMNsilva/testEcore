
### Running Database docker image (Mysql 8)

Required docker-compose v2.13.0
```
docker-compose -f src/main/docker/mysql-app.yml up -d
```
### Database
MySql 8.0 with default configuration drive:

```
jdbc:mysql://localhost:3306/ecore
```
Credentials:
<table>
    <tr>
        <th>Username</th>
        <th>Password</th>
    </tr>
    <tr>
        <td>root</td>
        <td>root</td>
    </tr>
</table>

The tables are being auto generated by spring jpa configuration.
You can check this configuration in the **application.properties:**
```
spring.jpa.hibernate.ddl-auto=update
```
### Running application
run:
```
mvn clean compile
```
and then
```
mvn spring-boot:run
```



### Java Version

```
11
```

### Test
Contains some simple unit tests and integration tests.

To run the tests:
```
mvn verify
```
### Swagger.ui
```
http://localhost:8085/swagger-ui.html
```

### Authentication
No needed. All endpoints are bypassing any authentication.

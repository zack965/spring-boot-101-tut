# Spring boot 101 
## Spring boot profiles 
### In sometimes we need to add some features in our applications , and this feature behave dynamically between dev and test and prod environment . let's say we need to call an external api to send emails but the endpoint of this api is different between different environments . In that case we can use spring boot profiles .
### First we define properties for dev and prod
- Dev
```properties
spring.application.name=justWebServer
server.port=8000

email.endpoint=http://127.0.0.1:8085/profiles
```
- Prod 
```properties
spring.application.name=justWebServer
server.port=8000

email.endpoint=http://127.0.0.1:8088/profiles
```
### Then in application properties we define the current active profile 
```properties
spring.application.name=justWebServer
server.port=8000
spring.profiles.active=dev
```
### Then we define class to get this data 
```java
package com.justWebServer.justWebServer.Config.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "email")
@Component
public class EmailConfig {
    private String endpoint;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}

```
### Finally, we can get this data by injecting the EmailConfig class in the controller 
```java
package com.justWebServer.justWebServer.Controllers;

import com.justWebServer.justWebServer.Config.Email.EmailConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("profiles")
@RestController
public class ProfileController {
    private final EmailConfig emailConfig;

    public ProfileController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @GetMapping
    public String hello(){
        return this.emailConfig.getEndpoint();
    }
}

```
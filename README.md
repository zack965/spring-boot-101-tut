# Spring boot 101 
## Dynamically Managing Features Across Environments with Spring Boot Profiles 
### In some cases, we need to add features to our applications that behave differently across environments—like development, testing, and production. One common scenario is integrating with an external API, where the endpoint might be different between environments. 
### A great way to handle this in Spring Boot is by leveraging profiles. Spring Boot profiles allow you to define environment-specific configurations that automatically adapt based on the environment your app is running in. This way, you can ensure your app connects to the right service, no matter the environment.
###  How does it work?
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
### And in application properties we define the current active profile 
```properties
spring.application.name=justWebServer
server.port=8000
spring.profiles.active=dev
```
### And we define class to get this data 
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
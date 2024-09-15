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

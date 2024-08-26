package com.g41.trashsmart_server;

import com.g41.trashsmart_server.Configuration.SMTPGmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    String hello() {
        return "Authenticated!";
    }

    @GetMapping("/api/authe/hello")
    @ResponseBody
    String helloAuth() {
        return "Hello!";
    }

    @Autowired
    private SMTPGmailSenderService smtpGmailSenderService;

    @GetMapping("/sendHello")
    public String sendHelloEmail() {
    String[] toEmails = {"example1@gmail.com", "example2@gmail.com"};
    String subject = "Hello from TrashSmart";
    String body = "Hello, Kohomada yaluwane? ! This is a greeting from TrashSmart Server.\nOyala kalada inne?";

    for (String toEmail : toEmails) {
        smtpGmailSenderService.sendEmail(toEmail, subject, body);
    }
    return "Hello emails sent successfully to " + String.join(", ", toEmails);
}


}


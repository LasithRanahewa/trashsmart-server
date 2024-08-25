package com.g41.trashsmart_server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}


package com.g41.trashsmart_server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/")
    @ResponseBody
    String hello() {
        return "Trashsmart Server is running!";
    }

}


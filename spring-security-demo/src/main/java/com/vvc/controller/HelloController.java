package com.vvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/HelloWorld")
    public String hello(){
        log.info("调用HelloWorld方法");
        return "Hello World";
    }
}

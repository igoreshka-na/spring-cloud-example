package com.example.userservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/user")
public class UserController {

    @GetMapping
    public String health() {
        return "OK and Peace";
    }
}
package com.nncompany.rest.controller;

import com.nncompany.api.model.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Test {

    public Test() {
        System.out.println("test");
    }

    @GetMapping
    public User getTestUser() {
        return User.builder().id(999).name("fff").build();
    }
}

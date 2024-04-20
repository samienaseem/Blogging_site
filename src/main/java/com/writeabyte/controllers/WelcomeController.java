package com.writeabyte.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/index")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to Write A Byte", HttpStatus.OK);
    }
}

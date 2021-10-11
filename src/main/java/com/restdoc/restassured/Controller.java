package com.restdoc.restassured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class Controller {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getString() {
        return "String";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createOrder(@RequestBody Note note) {
        return note;
    }
}

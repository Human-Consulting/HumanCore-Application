package com.humanconsulting.humancore_api.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class TesteController {

    @GetMapping
    public String teste(){
        return "Ok";
    }
}

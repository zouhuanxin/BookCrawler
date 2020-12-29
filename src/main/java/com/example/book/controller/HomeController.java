package com.example.book.controller;

import com.example.book.compant.BqgBookCompant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private BqgBookCompant bqgBookCompant;

    @GetMapping("getHome")
    public String getHome(){
        return bqgBookCompant.home();
    }

}

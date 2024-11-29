package com.signalapp.tradingsignalapp.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiControllers {

    @GetMapping(value = "/")
    public String getPage(){
        return "this will be main page";
    }

    @GetMapping(value = "/register")
    public String getRegisterPage(){
        return "this will be register page";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "this will be login page";
    }

}

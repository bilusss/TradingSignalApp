package com.signalapp.tradingsignalapp.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PagesController {

    @GetMapping(value = "/")
    public String getPage(){
        return "redirect:/register";
    }

    @GetMapping(value = "/home")
    public String getHomePage(){return "home";}

    @GetMapping(value = "/trade")
    public String getTradePage() {return "trade";}

    @GetMapping(value = "/char")
    public String getChartPage() {return "chart_v2";}

}



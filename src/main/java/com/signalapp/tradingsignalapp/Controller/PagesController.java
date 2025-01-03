package com.signalapp.tradingsignalapp.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping(value = "/trade/chart")
    public String getChartPage() {return "chart";}

    @GetMapping(value = "/transactions")
    public String getTransactionsPage() {return "transactions";}

    @GetMapping(value = "/balance")
    public String getBalancePage() {return "balance";}

}



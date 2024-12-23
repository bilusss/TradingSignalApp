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

    @GetMapping(value = "/chart")
    public String getChartPage() {return "chart_v2";}

    @GetMapping(value = "/transactions")
    public String getTransactionsPage() {return "transactions";}

    @GetMapping(value = "/balance")
    public String getBalancePage() {return "balance";}

    @GetMapping(value = "/wallet")
    public String getWalletPage() {return "wallet";}

}



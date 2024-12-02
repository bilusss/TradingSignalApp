package com.signalapp.tradingsignalapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ChartController {
    private final List<String> AvailablePairs;

    @Autowired
    public ChartController(List<String> AvailablePairs) {
        this.AvailablePairs = AvailablePairs;
    }

    @GetMapping("/chart/{pair}")
    public String getChart(@PathVariable String pair, Model model) {
        if (AvailablePairs.contains(pair)) {
            model.addAttribute("pair", pair);
            return "chart";
        } else {
            model.addAttribute("error", "Trading pair " + pair + " is not available.");
            return "error";
        }
    }
}

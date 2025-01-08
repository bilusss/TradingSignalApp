package com.signalapp.tradingsignalapp.Controller;


import com.signalapp.tradingsignalapp.Transaction.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    private final SessionController sessionController;
    public PagesController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @GetMapping(value = "/")
    public String getPage(){
        return "redirect:/register";
    }

    @GetMapping(value = "/home")
    public String getHomePage(HttpSession session,  Model model){
        Integer userId = sessionController.getSession(session);
        model.addAttribute("userId", userId );
        return "home";
    }

    @GetMapping(value = "/trade")
    public String getTradePage(HttpSession session, Model model) {
        Integer userId = sessionController.getSession(session);
        if (userId == null){
            return "redirect:/login";
        }
        model.addAttribute("userId", userId );
        return "trade";
    }

    @GetMapping(value = "/trade/chart")
    public String getChartPage(HttpSession session, Model model) {
        Integer userId = sessionController.getSession(session);
        if (userId == null){
            return "redirect:/login";
        }
        model.addAttribute("userId", userId );
        return "chart";
    }

    @GetMapping(value = "/transactions")
    public String getTransactionsPage(HttpSession session, Model model) {
        Integer userId = sessionController.getSession(session);
        if (userId == null){
            return "redirect:/login";
        }
        model.addAttribute("userId", userId );
        return "transactions";
    }

    @GetMapping(value = "/balance")
    public String getBalancePage(HttpSession session, Model model) {
        Integer userId = sessionController.getSession(session);
        if (sessionController.getSession(session) == null){
            return "redirect:/login";
        }
        model.addAttribute("userId", userId );
        return "balance";
    }

    @GetMapping(value = "/logout")
    public String Logout(HttpSession session){
        sessionController.clearSession(session);
        return "redirect:/home";
    }
}



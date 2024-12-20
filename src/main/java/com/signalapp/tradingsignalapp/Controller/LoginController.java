package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.User.User;
import com.signalapp.tradingsignalapp.User.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping(value = "/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
        Optional<User> loginUser = userRepository.getByUsername(username);

        if (loginUser.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        User user = loginUser.get();
        if(user.checkPassword(password)){
            session.setAttribute("userId", user.getId());
            return "redirect:/home";
        }else{
            model.addAttribute("error", "Invalid password");
            return "login";
        }
    }


}
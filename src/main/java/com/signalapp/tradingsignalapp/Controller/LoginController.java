package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.User.User;
import com.signalapp.tradingsignalapp.User.UserRepository;
import org.springframework.stereotype.Controller;
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
    public String handleLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<User> user = userRepository.getByUsername(username);
        if (user.isEmpty()) {
            // TODO return login with info
            return "redirect:/login";
        }
        if(user.get().checkPassword(password)){
            return "redirect:/home";
        }else{
            // TODO return login with info
            return "redirect:/login";
        }
    }


}
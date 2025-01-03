package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.User.User;
import com.signalapp.tradingsignalapp.User.UserController;
import com.signalapp.tradingsignalapp.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {

    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String handleRegistration(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (userRepository.getByUsername(username).isPresent()) {
            return "Error";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCredit(20000d);
        System.out.println(user);
        userRepository.create(user);
        return "redirect:/login";
    }


}
package com.signalapp.tradingsignalapp.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @RequestMapping("/set")
    public void setSession(HttpSession session, @RequestBody JsonNode json){
        Integer userId = json.get("userId").asInt();
        session.setAttribute("userId", userId);

    }

    @RequestMapping("/get")
    public Integer getSession(HttpSession session){
        try{
            return (Integer) session.getAttribute("userId");
        } catch ( Exception e ){
            return null;
        }
    }

    @RequestMapping("/clear")
    public void clearSession(HttpSession session){
        session.removeAttribute("userId");
    }
}

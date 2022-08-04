package com.example.intern6;

import com.example.intern6.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }
}

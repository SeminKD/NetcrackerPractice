package com.example.practicemav.controller;

import com.example.practicemav.model.User;
import com.example.practicemav.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String home(){
        return "start";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());
            if (userFromDb != null) {
                return "registration";
            } else {
                userRepository.save(user);
                return "redirect:/login";
            }
    }
}

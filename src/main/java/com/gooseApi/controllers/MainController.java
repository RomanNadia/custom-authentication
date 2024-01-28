package com.gooseApi.controllers;

import com.gooseApi.passwordHashing.PasswordHashing;
import com.gooseApi.models.Sessions;
import com.gooseApi.models.User;
import com.gooseApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Sessions session;
    @Autowired
    PasswordHashing passwordHashing;


    @GetMapping("/login")
    public String getLogin(Model model) {
        if(session.getUser() == null) {
            model.addAttribute("user", new User());
            return "login";
        } else {
            return "redirect:/pageForUsers";
        }
    }


    @GetMapping("/")
    public String getHome() {
        return "home";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) throws NoSuchAlgorithmException {
        User userToCompere = userRepository.findByUsername(user.getUsername());
        if(userToCompere != null) {
            if(passwordHashing.verifyPassword(user.getPassword(), userToCompere.getPassword())) {
                session.setUser(user);
                return "redirect:/pageForUsers";
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/register")
    public String getRegister(Model model) {
        if(session.getUser() == null) {
            model.addAttribute("user", new User());
            return "register";
        } else {
            return "redirect:/pageForUsers";
        }
    }


    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) throws NoSuchAlgorithmException {
        User userToCompere = userRepository.findByUsername(user.getUsername());
        if(userToCompere == null) {
            user.setPassword(passwordHashing.hashPassword(user.getPassword()));
            userRepository.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }


    @GetMapping("/pageForUsers")
    public String getPageForUsers() {
        if(session.getUser() != null) {
            return "pageForUsers";
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/logout")
    public String logout() {
        session.setUser(null);
        return "redirect:/login";
    }
}

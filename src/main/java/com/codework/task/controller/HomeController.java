package com.codework.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codework.task.entity.User;
import com.codework.task.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;



    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {

        boolean f = userService.existEmailCheck(user.getEmail());

        if(f){
            session.setAttribute("msg", "Email already exists");
        }
        else{
            userService.saveUser(user);
            if(user != null)
                session.setAttribute("msg", "User Registered");
            else 
                session.setAttribute("msg", "User register unsuccessfull");
        }
        return "redirect:/register";
    }


    @DeleteMapping("/removeUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/signin?logout")
    public String logout() {
        System.out.println("in logout");
        return "redirect:/signin";
    }

    
}

package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String loadSignUpPage(Model model){
        return "signup";
    }

    @PostMapping
    public String createUserAccount(@ModelAttribute User user, Model model){
        if(user==null)
            return "signup";
        Boolean signupError = false;
        Boolean signupSuccess = false;

        if(userService.isUsernameAvailable(user.getUsername())){
            signupError = true;
        }
        else{
            int rowsReturned = userService.createUser(user);
            if(rowsReturned>0) {
                signupSuccess = true;
            }
        }
        model.addAttribute("signupSuccess", signupSuccess);
        model.addAttribute("signupError", signupError);

        return "signup";


    }
}

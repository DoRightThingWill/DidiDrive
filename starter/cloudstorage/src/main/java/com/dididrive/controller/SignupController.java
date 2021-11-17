package com.dididrive.controller;


import com.dididrive.entity.User;
import com.dididrive.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;


    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup (){

        return "signup";
    }



    @PostMapping
    public String signup(Model model, @ModelAttribute User user){
        String signupError = null;


        System.out.println("username is "+user.getUsername());
        System.out.println("password is "+user.getPassword());
        if(!userService.isUsernameAvailable(user.getUsername()))
            signupError="Username already exists.";
        else {
            int insertID = userService.createUser(user);
            if(insertID<0)
                signupError="There was an error signing you up, please try again";
            else
                System.out.println("New userid is "+insertID);
        }

        if(signupError == null) {
            model.addAttribute("signupSuccess", true);

        }
        else
            model.addAttribute("signupError",signupError);

        return "signup";
    }
}

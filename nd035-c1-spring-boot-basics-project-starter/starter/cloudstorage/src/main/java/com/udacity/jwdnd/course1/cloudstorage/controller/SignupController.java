package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {


    private final UserService userService;

    public SignupController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
    }
    @GetMapping()
    public String getSignup(){
        return "signup";
    }

    @PostMapping()
    public String signup(Model model, User user, RedirectAttributes redirectAttributes){
        String errorMessage = "";
     if (!userService.isUserExist(user.getUsername())){
         userService.createUser(user);
         errorMessage = "success";
         redirectAttributes.addFlashAttribute("successfulSignup",true);
         model.addAttribute("display",errorMessage);
         return "redirect:/login?success";
     }
     else {
      errorMessage = "error";
         model.addAttribute("display",errorMessage);
         return "signup";
     }

    }
}

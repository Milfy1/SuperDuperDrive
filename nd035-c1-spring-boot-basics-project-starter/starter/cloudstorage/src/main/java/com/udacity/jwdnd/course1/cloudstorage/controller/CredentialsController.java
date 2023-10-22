package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {
    private final CredentialService  credentialService;
    private final UserService userService;

//    private final

    public CredentialsController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    @GetMapping("/deleteCredentials")
    public String deleteCredential(Integer credId, RedirectAttributes redirectAttributes){
        credentialService.deleteCredentials(credId);
        redirectAttributes.addFlashAttribute("successfulDeleteCredentials", true);
        return "redirect:/home";
    }


    @PostMapping("/addCredential")
    public String addCredential(Credentials credentials, Authentication authentication,RedirectAttributes redirectAttributes) {
       Integer userId = userService.getUserId(authentication.getName());
        credentials.setUserid(userId);

           credentialService.addCredentials(credentials);
        redirectAttributes.addFlashAttribute("successfulChangesCredentials",true);




        return "redirect:/home";

    }
}

package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class homeController {
    private final FileService fileService;
    private final UserService userServise;
    private final NoteService noteService;

    private  final CredentialService credentialService;
    public homeController(FileService fileService, UserService userServise, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userServise = userServise;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
     public String getHome(Model model, Authentication authentication){

       Integer userId = userServise.getUserId(authentication.getName());
       ArrayList<File> files = fileService.getFiles(userId);
       ArrayList<Note> notes = noteService.getNotes(userId);
       model.addAttribute("files",files);
       model.addAttribute("notes",notes);
       model.addAttribute("credentials",credentialService.getCredentials(userId));
      model.addAttribute("Cred",credentialService.decryptAll(credentialService.getCredentials(userId)));


        return "home";
    }



}

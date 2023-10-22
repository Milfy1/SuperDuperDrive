package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
private final UserService userService;
private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/addNote")
    public String addNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes){

        Integer userId = userService.getUserId(authentication.getName());
        note.setUserid(userId);
        if (note.getNoteDescription().length() >= 1000){
            redirectAttributes.addFlashAttribute("errorAddNote",true);
            return "redirect:/home";
        }

        if(note.getNoteid() == null){
            noteService.addNote(note);
        } else {
            noteService.editNote(note);
        }
        redirectAttributes.addFlashAttribute("successfulChangesNote",true);



        return "redirect:/home";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam() Integer noteId,RedirectAttributes redirectAttributes){
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("successfulDeleteNote", true);
        return "redirect:/home";
    }
}

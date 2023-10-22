package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class FileController{

    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/addFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile File, Authentication authentication) throws IOException, SQLException {

        System.out.println(File.getSize());
if (File.getSize() >= 10485760){
    return "redirect:/home?large";
        }

        Integer userId = userService.getUserId(authentication.getName());
        if(File.isEmpty()){
            return "redirect:/home?empty";
        }
       String error = fileService.uploadFile(userId,File);
       if(error.contentEquals("There is a file with the same name"))
           return "redirect:/home?error";
        return "redirect:/home?success";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam() Integer fileId){
       fileService.deleteFile(fileId);
       System.out.println("S");
        return "redirect:/home?success";

    }

    @GetMapping("/viewFile")
    public ResponseEntity viewFile(@RequestParam() Integer fileId){
      File file = fileService.viewFile(fileId);
        return ResponseEntity.ok().header(file.getFileName()).contentLength(file.getFileSize()).contentType(MediaType.parseMediaType(file.getContentType())).body(file.getFileData());
    }

}

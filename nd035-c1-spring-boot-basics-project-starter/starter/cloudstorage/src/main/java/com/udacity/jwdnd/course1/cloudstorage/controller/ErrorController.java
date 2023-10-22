package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends AbstractErrorController {
    private static final String ERROR_PATH=  "/error";
    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFound() {
        return "404 - please enter a valid url";
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<?> errors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return ResponseEntity.status(status).body(getErrorAttributes(request, false));
    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.ForgotPasswordDto;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete/acc")
    public ResponseEntity<String> deleteAcc(@RequestBody String email){
        String emailNoQuotes = email.substring(1,email.length()-1);
        return userService.deleteUser(emailNoQuotes);
    }

    @PostMapping("/reset-password")
    public String getResetPassPage(@RequestBody ForgotPasswordDto passDto) {

        return userService.resetPassword(passDto.getToken(), passDto.getPassword());
    }

}

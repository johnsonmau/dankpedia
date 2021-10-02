package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.ForgotPasswordDto;
import com.johnson.cannabis.models.ForgotPasswordEmailDto;
import com.johnson.cannabis.models.User;
import com.johnson.cannabis.services.EmailService;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ForgotPasswordController {

    @Value("${email.reset.link}")
    private String emailResetLink;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @ModelAttribute("password")
    public ForgotPasswordDto forgotPasswordDto(){
        return new ForgotPasswordDto();
    }

    @ModelAttribute("email")
    public ForgotPasswordEmailDto forgotPasswordEmailDto() { return new ForgotPasswordEmailDto(); }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(required = false) String token) {

        return "resetPassword";
    }

    @GetMapping("/forgot")
    public String forgotPasswordPage(){

        return "forgotPassword";
    }

    @PostMapping("/forgot")
    public String forgotPasswordPagePost(@ModelAttribute("email") @Valid ForgotPasswordEmailDto forgotPasswordEmailDto, BindingResult result){

        User emailExists = userService.findByEmail(forgotPasswordEmailDto.getEmail());

        if (emailExists == null && forgotPasswordEmailDto.getEmail().length() != 0){
            result.rejectValue("email", null, "Sorry, that email is not registered with Dankpedia");
        }

        if (result.hasErrors()){
            return "forgotPassword";
        }

        String passResetLink = emailResetLink+"/reset-password?token="+userService.forgotPassword(forgotPasswordEmailDto.getEmail());

        emailService.sendSimpleMessage(forgotPasswordEmailDto.getEmail(), "Reset your Dankpedia password",
                "Click the link to reset your password: "+passResetLink);

        return "redirect:/forgot?success";
    }
}

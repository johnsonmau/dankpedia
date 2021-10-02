package com.johnson.cannabis.controllers;

import javax.validation.Valid;

import com.johnson.cannabis.models.User;
import com.johnson.cannabis.models.UserRegistrationDto;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){

        String userPass = userDto.getPassword();

        User emailExists = userService.findByEmail(userDto.getEmail());
        User usernameExists = userService.findByUsername(userDto.getUsername());


        if (emailExists != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (usernameExists != null){
            result.rejectValue("username", null, "There is already an account registered with that username");
        }

        if (userPass.length() < 8){
            result.rejectValue("password", null, "Password must be a minimum of 8 characters long.");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

}

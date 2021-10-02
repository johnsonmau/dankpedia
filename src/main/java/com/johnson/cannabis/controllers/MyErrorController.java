package com.johnson.cannabis.controllers;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import com.johnson.cannabis.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

    @Autowired
    private UserService userService;

    @GetMapping("/error")
    public String errPage(Model model){
        User user = getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private User getLoggedInUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        String emailAddr = loggedInUser.getName();

        User user = userService.findByEmail(emailAddr);

        return user;
    }
}

package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.models.User;
import com.johnson.cannabis.services.StrainServiceImpl;
import com.johnson.cannabis.services.ReviewService;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private StrainServiceImpl strainServiceImpl;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String index(Model model) {

        User user = getLoggedInUser();
        String usersEmail;
        String usersFullName;

        if (user == null){
            usersEmail = "";
            usersFullName = "";
        } else {
            usersEmail = user.getEmail();
            usersFullName = user.getFirstName() + " " + user.getLastName();
        }

        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");
        model.addAttribute("theEmail", usersEmail);
        model.addAttribute("fullName", usersFullName);

        if (user == null) {
            model.addAttribute("disabled", true);
        } else {
            model.addAttribute("disabled", false);
        }

        return "index";
    }

    @GetMapping("/dashboard")
    public String getDash(Model model) {

        User user = getLoggedInUser();

        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");
        model.addAttribute("favorites", user.getFavorites());
        model.addAttribute("favoritesListSize", user.getFavorites().size());


        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");
        model.addAttribute("myReviews", reviewService.findReviewByUsername(user.getUsername()));
        model.addAttribute("myReviewsSize", reviewService.findReviewByUsername(user.getUsername()).size());

        if (user == null) {
            model.addAttribute("disabled", true);
        } else {
            model.addAttribute("disabled", false);
        }

        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/terms")
    public String getTerms(Model model) {

        User user = getLoggedInUser();

        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");

        return "terms";
    }

    @GetMapping("/privacy")
    public String getPrivacy(Model model) {

        User user = getLoggedInUser();

        model.addAttribute("user", user);
        model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");

        return "privacy";
    }

    @GetMapping("/strain/rest/{theStrain}")
    public String getStrainPage(Model model, @PathVariable String theStrain) {

        User user = getLoggedInUser();
        String username = new String();

        if (user == null) {
            username = "";
        }

        try {

            Strains strainObj = strainServiceImpl.getStrainByName(theStrain);
            String type = strainObj.getSType().substring(0, 1).toUpperCase() + strainObj.getSType().substring(1);
            String htmlStr;

            model.addAttribute("strain", strainObj.getStrain());
            model.addAttribute("desc", strainObj.getDescr());
            model.addAttribute("rating", strainObj.getRating());
            model.addAttribute("effects", strainObj.getEffects());
            model.addAttribute("flavor", strainObj.getFlavor());
            model.addAttribute("type", type);
            model.addAttribute("user", user);
            model.addAttribute("theHtml", "<i class=\"fa fa-user-o fa-lg\" aria-hidden=\"true\"></i>\n");
            model.addAttribute("disabled", false);
            model.addAttribute("reviews", strainObj.getReviews());
            model.addAttribute("reviewListSize", strainObj.getReviews().size());

            if (user != null) {
                username = user.getUsername();
            }

            model.addAttribute("username", username);

            if (strainObj.getUsersFavorite() == true) {
                htmlStr = "<i class=\"fa fa-heart\" style=\"color: red\" aria-hidden=\"true\"></i> remove from favorites";
            } else {
                htmlStr = "<i class=\"fa fa-heart-o\" style=\"color: black\" aria-hidden=\"true\"></i> add to favorites";
            }

            model.addAttribute("favoriteLink", htmlStr);

            return "strain";
        } catch (Exception ex) {

            model.addAttribute("favoriteLink", "<i disbaled class=\"fa fa-heart-o\" style=\"color: black\" aria-hidden=\"true\"></i> add to favorites");
            model.addAttribute("disabled", true);
            model.addAttribute("username", username);

            if (strainServiceImpl.getStrainByName(theStrain) == null) return "redirect:/";

            return "strain";
        }
    }

    private User getLoggedInUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        String emailAddr = loggedInUser.getName();

        User user = userService.findByEmail(emailAddr);

        return user;
    }

}

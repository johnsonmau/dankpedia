package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.FavoriteStrain;
import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.models.User;
import com.johnson.cannabis.services.ReviewService;
import com.johnson.cannabis.services.StrainService;
import com.johnson.cannabis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StrainRestController {


    @Autowired
    private StrainService strainService;

    @Autowired
    private UserService userService;

    @GetMapping("/strains")
    public List<Strains> getStrains(){
        return strainService.getAllStrains();
    }

    @GetMapping("/strains/{id}")
    public Strains getStrains(@PathVariable Long id){
        return strainService.getStrain(id);
    }

    @GetMapping("/strains/strain")
    public Strains getStrainByName(@RequestParam String strain){
        return strainService.getStrainByName(strain);
    }

    @GetMapping("/strains/type")
    public List<Strains> searchStrainType(@RequestParam String type, @RequestParam String keyword, @RequestParam String sortType, @PageableDefault(value = 10, page = 0)Pageable pageable){
        return strainService.searchStrainType(type, keyword, pageable, sortType);
    }

    @PostMapping("/strains/favorites")
    public User addToFavorites(@RequestBody List<FavoriteStrain> strains){
        return userService.addToFavorites(strains);
    }

    @DeleteMapping("/strains/removeFavorite")
    public User removeFromFavorites(@RequestBody List<FavoriteStrain> strains){
        return userService.removeFromFavorites(strains);
    }

}

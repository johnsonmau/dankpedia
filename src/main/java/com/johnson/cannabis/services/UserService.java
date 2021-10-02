package com.johnson.cannabis.services;

import com.johnson.cannabis.models.FavoriteStrain;
import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.models.User;
import com.johnson.cannabis.models.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User findByUsername(String username);

    User save(UserRegistrationDto registration);

    User addToFavorites(List<FavoriteStrain> strains);

    User removeFromFavorites(List<FavoriteStrain> strains);

    ResponseEntity<String> deleteUser(String email);

    String forgotPassword(String email);

    String resetPassword(String token, String password);

}

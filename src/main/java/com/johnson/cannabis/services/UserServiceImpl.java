package com.johnson.cannabis.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.johnson.cannabis.models.*;
import com.johnson.cannabis.repositories.StrainRepository;
import com.johnson.cannabis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StrainRepository strainRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    public String forgotPassword(String email) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));

        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepository.save(user);

        return user.getToken();
    }

    public String resetPassword(String token, String password) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByToken(token));


        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        User user = userOptional.get();

        user.setPassword(passwordEncoder.encode(password));
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepository.save(user);

        return "Your password successfully updated.";
    }

    /**
     * Generate unique token. You may add multiple parameters to create a strong
     * token.
     *
     * @return unique token
     */
    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    /**
     * Check whether the created token expired or not.
     *
     * @param tokenCreationDate
     * @return true or false
     */
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    public ResponseEntity<String> deleteUser(String email) {

        try {
            User user = userRepository.findByEmail(email);
            userRepository.delete(user);

            return new ResponseEntity<>("User deleted.", HttpStatus.OK);
        } catch (Exception ex){

            return new ResponseEntity<>("Could not delete user.", HttpStatus.BAD_REQUEST);

        }
    }

    public User addToFavorites(List<FavoriteStrain> strains) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String emailAddr = loggedInUser.getName();
        Strains theStrain = strainRepository.findByStrain(strains.get(0).getStrain());

        User user = userRepository.findByEmail(emailAddr);

        Collection<Strains> userFavorites = user.getFavorites();

        userFavorites.add(theStrain);

        return userRepository.save(user);
    }

    public User removeFromFavorites(List<FavoriteStrain> strains) {

        try {

            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            String emailAddr = loggedInUser.getName();

            User user = userRepository.findByEmail(emailAddr);

            Collection<Strains> userFavorites = user.getFavorites();

            for (int i = 0; i < strains.size(); i++) {
                FavoriteStrain fs = strains.get(i);
                userFavorites.removeIf(t -> t.getStrain().equalsIgnoreCase(fs.getStrain()));
            }

            user.setFavorites(userFavorites);

            return userRepository.save(user);

        } catch (Exception ex) {

        }


        User user = new User();
        user.setFirstName("Error, user does not exist.");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

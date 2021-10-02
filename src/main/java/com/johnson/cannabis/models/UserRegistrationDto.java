package com.johnson.cannabis.models;

import com.johnson.cannabis.constraint.FieldMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserRegistrationDto {

    @NotEmpty(message = "Please enter a first name.")
    private String firstName;

    @NotEmpty(message = "Please enter a last name.")
    private String lastName;

    @NotEmpty(message = "Please enter a password.")
    private String password;

    @NotEmpty(message = "Please enter a password.")
    private String confirmPassword;

    @NotEmpty(message = "Please enter a username.")
    private String username;

    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Please enter an email.")
    private String email;

    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Please enter an email.")
    private String confirmEmail;

    @AssertTrue(message = "Please agree to the terms and conditions.")
    private Boolean terms;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

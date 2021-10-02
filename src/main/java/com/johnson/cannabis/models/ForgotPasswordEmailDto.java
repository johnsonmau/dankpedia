package com.johnson.cannabis.models;

import javax.validation.constraints.NotEmpty;

public class ForgotPasswordEmailDto {

    @NotEmpty(message = "You must enter an email address.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

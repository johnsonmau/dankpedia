package com.johnson.cannabis.models;

public class Email {

    private String name;
    private String email;
    private String comments;

    public Email(String name, String email, String comments) {
        this.name = name;
        this.email = email;
        this.comments = comments;
    }

    public Email() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

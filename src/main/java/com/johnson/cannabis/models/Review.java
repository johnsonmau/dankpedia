package com.johnson.cannabis.models;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String body;

    @Column
    private String user;

    @Column
    private String createdDate;

    @Column
    private String updatedDate;

    @Column
    private String strain;

    @Column
    private Double rating;

    public Review(String body, String user, String createdDate, String updatedDate, String strain, long id, Double rating) {
        this.body = body;
        this.user = user;
        this.createdDate = createdDate;
        this.id = id;
        this.strain = strain;
        this.updatedDate = updatedDate;
        this.rating = rating;
    }

    public Review() {
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}

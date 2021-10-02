package com.johnson.cannabis.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Strains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String strain;

    @Column(name = "s_type")
    private String sType;

    @Column
    private String effects;

    @Column
    private Double rating;

    @Column
    private String flavor;

    @Column
    private String descr;

    private Boolean usersFavorite;

    @ManyToMany
    @JoinTable(
            name = "strain_review",
            joinColumns = @JoinColumn(
                    name = "strain_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "review_id", referencedColumnName = "id"))
    private Collection<Review> reviews;


    public Strains(long id, String strain, String sType, String effects, Double rating, String flavor, String descr, Collection<Review> reviews) {
        this.id = id;
        this.strain = strain;
        this.sType = sType;
        this.effects = effects;
        this.flavor = flavor;
        this.descr = descr;
        this.rating = rating;
        this.reviews = reviews;
    }


    public Strains() {
    }
    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Boolean getUsersFavorite() {
        return usersFavorite;
    }

    public void setUsersFavorite(Boolean usersFavorite) {
        this.usersFavorite = usersFavorite;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getSType() {
        return sType;
    }

    public void setSType(String s_type) {
        this.sType = sType;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

}

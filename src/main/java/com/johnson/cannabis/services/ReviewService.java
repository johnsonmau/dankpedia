package com.johnson.cannabis.services;

import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.Strains;

import java.util.List;

public interface ReviewService {

    Strains addReview(Review review);

    void deleteReview(Review review);

    Review updateReview(Review review);

    List<Review> findReviewByUsername(String user);

    Review save(Review review);

    Review findReviewById(Long id);
}

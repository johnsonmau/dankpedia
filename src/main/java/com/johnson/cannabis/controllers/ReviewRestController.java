package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews/add")
    public Strains addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PostMapping("/reviews/update")
    public Review updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/reviews/delete")
    public void deleteReview(@RequestBody Review review) {
        reviewService.deleteReview(review);
    }

    @GetMapping("/reviews/{id}")
    public Review getReview(@PathVariable Long id) {
        return reviewService.findReviewById(id);
    }
}

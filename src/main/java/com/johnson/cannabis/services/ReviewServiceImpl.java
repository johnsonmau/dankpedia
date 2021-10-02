package com.johnson.cannabis.services;

import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.repositories.StrainRepository;
import com.johnson.cannabis.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private StrainRepository strainRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Strains addReview(Review review) {
        Strains strains = strainRepository.findByStrain(review.getStrain());

        Collection<Review> reviews = strains.getReviews();

        reviews.add(reviewRepository.save(review));

        return strainRepository.save(strains);
    }

    @Override
    public void deleteReview(Review review) {
        Review reviewToDelete = reviewRepository.findById(review.getId()).get();
        reviewRepository.delete(reviewToDelete);
    }

    @Override
    public Review updateReview(Review review) {

        Review reviewToUpdate = reviewRepository.findById(review.getId()).get();
        reviewToUpdate.setBody(review.getBody());
        reviewToUpdate.setUpdatedDate(review.getUpdatedDate());

        return reviewRepository.save(reviewToUpdate);
    }

    @Override
    public List<Review> findReviewByUsername(String user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).get();
    }

}

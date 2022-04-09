package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.application;

import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.domain.Review;
import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }
}

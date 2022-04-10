package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public void delete(Review review){
        reviewRepository.delete(review);
    }
}

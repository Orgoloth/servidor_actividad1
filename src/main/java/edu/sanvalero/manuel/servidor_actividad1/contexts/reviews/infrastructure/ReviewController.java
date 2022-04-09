package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.infrastructure;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain.Movie;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain.MovieApiResponseError;
import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.application.ReviewService;
import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.domain.Review;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.application.UserService;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;

import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.API_KEY;
import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.BASE_URL;

@Controller
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @GetMapping("/reviews/new")
    public String newReview(Model model, RestTemplate restTemplate, @RequestParam String movieId) {
        if (movieId == null) return "redirect:/";

        String requestUrl = String.format("%s%s%s?api_key=%s&language=%s", BASE_URL, "/movie/", movieId, API_KEY, LocaleContextHolder.getLocale());
        try {
            Movie movie = restTemplate.getForObject(requestUrl, Movie.class);
            model.addAttribute("movie", movie);
        } catch (RestClientException e) {
            logger.info(e.getLocalizedMessage());
            throw new MovieApiResponseError(e.getLocalizedMessage());
        }
        model.addAttribute("review", new Review());
        return "reviews/new";
    }

    @PostMapping("/reviews/new")
    public String createReview(RestTemplate restTemplate, @RequestParam String movieId, @ModelAttribute Review review, HttpServletRequest request) {
        String remoteUsername = request.getRemoteUser();
        User remoteUser = userService.findByUsername(remoteUsername);
        review.setMovieId(Long.parseLong(movieId));
        review.setAuthor(remoteUser);
        review.setDate(LocalDate.now());
        reviewService.save(review);
        return "redirect:/";
    }

    @GetMapping("/reviews/list")
    public String getAll(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "reviews/list";
    }
}
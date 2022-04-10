package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.Movie;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.MovieApiResponseError;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.MovieService;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.User;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.UserService;
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
    private static final String NEW_PAGE = "/reviews/new";
    private static final String LIST_PAGE = "/reviews/list";
    private static final String SHOW_PAGE = "/reviews/show";
    private static final String DELETE_PAGE = "/reviews/delete";

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @GetMapping(NEW_PAGE)
    public String newReview(@RequestParam String movieId, Model model, RestTemplate restTemplate) {
        if (movieId == null) return "redirect:/";
        Movie movie = movieService.findById(Long.parseLong(movieId)).orElse(fetchMovieById(movieId, restTemplate)); // El fetch se ocupara de salvarla localmente para futuras llamadas

        model.addAttribute("movie", movie);
        model.addAttribute("review", new Review());
        return NEW_PAGE;
    }


    @GetMapping(LIST_PAGE)
    public String list(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return LIST_PAGE;
    }

    @GetMapping(SHOW_PAGE)
    public String show(@RequestParam String id, Model model) {
        Review review = reviewService.findById(Long.parseLong(id)).orElseThrow();
        Movie movie = review.getMovie();

        model.addAttribute("review", review);
        model.addAttribute("movie", movie);
        return SHOW_PAGE;
    }

    @GetMapping(DELETE_PAGE)
    public String delete(@RequestParam String id) {
        Review review = reviewService.findById(Long.parseLong(id)).orElseThrow();
        reviewService.delete(review);
        return "redirect:" + LIST_PAGE;
    }

    @PostMapping(NEW_PAGE)
    public String createReview(@RequestParam String movieId, @ModelAttribute Review review, HttpServletRequest request, RestTemplate restTemplate) {
        String remoteUsername = request.getRemoteUser();
        User remoteUser = userService.findByUsername(remoteUsername);
        Movie movie = movieService.findById(Long.parseLong(movieId)).orElse(fetchMovieById(movieId, restTemplate));
        review.setMovie(movie);
        review.setAuthor(remoteUser);
        review.setDate(LocalDate.now());
        reviewService.save(review);
        return "redirect:" + LIST_PAGE;
    }

    private Movie fetchMovieById(String movieId, RestTemplate restTemplate) {
        String requestUrl = String.format("%s%s%s?api_key=%s&language=%s", BASE_URL, "/movie/", movieId, API_KEY, LocaleContextHolder.getLocale());
        try {
            Movie movie = restTemplate.getForObject(requestUrl, Movie.class);
            logger.info(String.format("Recuperada la película %s de moviedb", movie.getTitle()));
            return movieService.save(movie);
        } catch (RestClientException e) {
            logger.info(e.getLocalizedMessage());
            throw new MovieApiResponseError(e.getLocalizedMessage());
        }
    }
}
package edu.sanvalero.manuel.servidor_actividad1.contexts.notes;

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
import java.util.Objects;

import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.API_KEY;
import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.BASE_URL;

@Controller
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private static final String NEW_PAGE = "notes/new";
    private static final String LIST_PAGE = "notes/list";
    private static final String SHOW_PAGE = "notes/show";

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/notes/new")
    public String newNote(@RequestParam String movieId, Model model, RestTemplate restTemplate) {
        if (movieId == null) return "redirect:/";
        Movie movie = movieService.findById(Long.parseLong(movieId)).orElse(fetchMovieById(movieId, restTemplate));

        model.addAttribute("movie", movie);
        model.addAttribute("note", new Note());
        return NEW_PAGE;
    }

    @GetMapping("/notes/list")
    public String list(Model model) {
        model.addAttribute("notes", noteService.findAll());
        return LIST_PAGE;
    }

    @GetMapping("/notes/show")
    public String show(@RequestParam String id, Model model) {
        Note note = noteService.findById(Long.parseLong(id)).orElseThrow();
        Movie movie = note.getMovie();

        model.addAttribute("note", note);
        model.addAttribute("movie", movie);
        return SHOW_PAGE;
    }

    @GetMapping("/notes/delete")
    public String delete(@RequestParam String id) {
        Note note = noteService.findById(Long.parseLong(id)).orElseThrow();
        noteService.delete(note);
        return "redirect:/notes/list";
    }

    @PostMapping("notes/new")
    public String createNote(@RequestParam String movieId, @ModelAttribute Note note, HttpServletRequest request, RestTemplate restTemplate) {
        String remoteUsername = request.getRemoteUser();
        User remoteUser = userService.findByUsername(remoteUsername);
        Movie movie = movieService.findById(Long.parseLong(movieId)).orElse(fetchMovieById(movieId, restTemplate));
        note.setMovie(movie);
        note.setAuthor(remoteUser);
        note.setDate(LocalDate.now());
        noteService.save(note);
        return "redirect:/notes/list";
    }

    private Movie fetchMovieById(String movieId, RestTemplate restTemplate) {
        String requestUrl = String.format("%s%s%s?api_key=%s&language=%s",
                BASE_URL,
                "/movie/",
                movieId,
                API_KEY,
                LocaleContextHolder.getLocale());
        try {
            Movie movie = restTemplate.getForObject(requestUrl, Movie.class);
            logger.info(String.format("Recuperada la película %s de moviedb", Objects.requireNonNull(movie).getTitle()));
            return movieService.save(movie);
        } catch (RestClientException e) {
            logger.info(e.getLocalizedMessage());
            throw new MovieApiResponseError(e.getLocalizedMessage());
        }
    }
}
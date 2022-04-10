package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.Movie;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String title;
    String text;
    LocalDate date;
    @Column(nullable = false)
    int rating;
    @OneToOne
    Movie movie;
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;
}

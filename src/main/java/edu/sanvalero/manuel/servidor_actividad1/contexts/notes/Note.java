package edu.sanvalero.manuel.servidor_actividad1.contexts.notes;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.Movie;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String title;
    String text;
    LocalDate date;
    boolean seen;
    @Column(name = "budget_guess")
    float budgetGuess;
    int stars;

    @OneToOne
    Movie movie;
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;
}

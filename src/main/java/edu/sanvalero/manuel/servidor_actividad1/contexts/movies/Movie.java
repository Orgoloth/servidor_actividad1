package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "movies")
public class Movie {
    @Id
    @Column(nullable = false)
    private Long id;
    private int popularity;
    private String original_title;
    private String title;
    @Lob private String overview;
    private String poster_path;

}


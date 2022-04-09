package edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "movies")
public class Movie {
    @Id
    @Column(nullable = false)
    private int id;
    private int popularity;
    private String original_title;
    private String title;
    private String overview;
    private String poster_path;

}


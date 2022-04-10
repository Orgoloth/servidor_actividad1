package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieApiPaginatedResponse {
    private int page;
    private int total_pages;
    private int total_results;
    private Movie[] results;
}

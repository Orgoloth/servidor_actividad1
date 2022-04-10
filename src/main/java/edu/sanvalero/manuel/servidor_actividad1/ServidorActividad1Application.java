package edu.sanvalero.manuel.servidor_actividad1;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.MovieApiResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class ServidorActividad1Application {
	private static Logger logger = LoggerFactory.getLogger(ServidorActividad1Application.class);

	public static void main(String[] args) {
		SpringApplication.run(ServidorActividad1Application.class, args);
	}

	@ExceptionHandler(MovieApiResponseError.class)
	public String handleException(HttpServletRequest request, Exception exception) {
		logger.info("Recibida excepción en app");
		return "error";
	}
}

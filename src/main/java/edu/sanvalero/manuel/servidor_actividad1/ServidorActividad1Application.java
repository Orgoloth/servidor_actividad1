package edu.sanvalero.manuel.servidor_actividad1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServidorActividad1Application {
    private static Logger logger = LoggerFactory.getLogger(ServidorActividad1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(ServidorActividad1Application.class, args);
    }
}

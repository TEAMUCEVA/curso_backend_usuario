package co.edu.uceva.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class Ejemplo01Application {

    public static void main(String[] args) {

        SpringApplication.run(Ejemplo01Application.class, args);
    }
}

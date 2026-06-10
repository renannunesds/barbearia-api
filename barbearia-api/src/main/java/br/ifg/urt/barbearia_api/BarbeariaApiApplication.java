package br.ifg.urt.barbearia_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching; // <--- IMPORTANTE

@EnableCaching
@SpringBootApplication

public class BarbeariaApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BarbeariaApiApplication.class, args);
    }
}
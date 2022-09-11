package com.tehcman;
//https://www.youtube.com/watch?v=MtKyDKCzENU&t=449s

//PR ME
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tehcman.cahce")
@EntityScan("com.tehcman.entities")
public class TgBotUhelpApplication {
    public static void main(String[] args) {
        SpringApplication.run(TgBotUhelpApplication.class, args);
    }
}

package com.letscode.moveisbattle;

import com.letscode.moveisbattle.model.AppUser;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.enums.UserRole;
import com.letscode.moveisbattle.repository.MovieRepository;
import com.letscode.moveisbattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MoveisbattleApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MoveisbattleApplication.class, args);
    }

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public MoveisbattleApplication(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Movie m1 = new Movie("1", "tt10986410", "Ted Lasso", 8.8);
        Movie m2 = new Movie("2", "tt3076658", "Creed", 7.6);
        Movie m3 = new Movie("3", "tt3397884", "Sicario", 7.6);
        Movie m4 = new Movie("4", "tt3460252", "The Hateful Eight", 7.8);
        Movie m5 = new Movie("5", "tt3553976", "Captain Fantastic", 7.9);
        Movie m6 = new Movie("6", "tt2119532", "Hacksaw Ridge", 8.1);
        Movie m7 = new Movie("7", "tt3450958", "War for the Planet of the Apes", 7.4);
        Movie m8 = new Movie("8", "tt9288030", "Reacher", 8.3);
        Movie m9 = new Movie("9", "tt8706598", "The Hitman's Bodyguard: The Hitman vs. The Bodyguard", 6.9);
        Movie m10 = new Movie("10", "tt11126994", "Arcane", 9.1);

        movieRepository.saveAll(Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));

        AppUser appUserPedro = new AppUser("Pedro", "Santos", "pedro@mail.com", "pedro", UserRole.USER);
        appUserPedro.setEnabled(true);
        appUserPedro.setLocked(false);
        appUserPedro.setPassword("$2a$10$1y/e8qyb6R5vpldh.JrItujQG.xheUAHg2vFUtI0sEzCUtI3.qj9O");

        AppUser appUserAna = new AppUser("Ana", "De Oliveira", "ana@mail.com", "ana", UserRole.USER);
        appUserAna.setEnabled(true);
        appUserAna.setLocked(false);
        appUserAna.setPassword("$2a$10$wourUIpMjMd0i4g2yswa7OTrGvElA.TEirOj1S3RcwAKRHr8VV6La");

        userRepository.saveAll(Arrays.asList(appUserAna, appUserPedro));
    }
}

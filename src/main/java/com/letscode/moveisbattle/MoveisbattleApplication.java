package com.letscode.moveisbattle;

import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.repository.MovieRepository;
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

	@Autowired
	public MoveisbattleApplication(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	String movie = "movie";

	@Override
	public void run(String... args) throws Exception {
		Movie m1 = new Movie("1", "tt10986410", "Ted Lasso", 2020, 8.8, "Comedy, Drama, Sport", "series");
		Movie m2 = new Movie("2", "tt3076658", "Creed", 2015, 7.6, "Action, Drama, Sport", movie);
		Movie m3 = new Movie("3", "tt3397884", "Sicario", 2015, 7.6, "Action, Crime, Drama", movie);
		Movie m4 = new Movie("4", "tt3460252", "The Hateful Eight", 2015, 7.8, "Crime, Drama, Mystery", movie);
		Movie m5 = new Movie("5", "tt3553976", "Captain Fantastic", 2016, 7.9, "Comedy, Drama", movie);
		Movie m6 = new Movie("6", "tt2119532", "Hacksaw Ridge", 2016, 8.1, "Biography, Drama, History", movie);
		Movie m7 = new Movie("7", "tt3450958", "War for the Planet of the Apes", 2017, 7.4, "Action, Adventure, Drama", movie);

		movieRepository.saveAll(Arrays.asList(m1, m2, m3, m4, m5, m6, m7));
	}
}

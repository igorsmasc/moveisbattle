package com.letscode.moveisbattle.service.impl;

import com.letscode.moveisbattle.dto.MovieDTO;
import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.service.MovieService;
import com.letscode.moveisbattle.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final MovieService movieService;

    private Random rand;

    public QuestionServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Question generateQuestion(Game game) {

        boolean validMovies = false;

        Integer m1 = null;
        Integer m2 = null;
        String questionHash01;
        String questionHash02;


        while (!validMovies) {
            rand = new Random();
            m1 = rand.nextInt((7 - 1) + 1) + 1;
            m2 = rand.nextInt((7 - 1) + 1) + 1;
            questionHash01 = game.getId() + m1 + m2;
            questionHash02 = game.getId() + m2 + m1;

            if (!game.getPreviousQuestions().contains(questionHash01) && !game.getPreviousQuestions().contains(questionHash02)) {
                validMovies = true;
            }
        }

        Optional<Movie> optionalMovie01 = movieService.getMovie(String.valueOf(m1));
        Optional<Movie> optionalMovie02 = movieService.getMovie(String.valueOf(m2));

        if (optionalMovie01.isEmpty() || optionalMovie02.isEmpty()) {
            throw new IllegalStateException("Invalid movie!");
        }


        return new Question(game.getId(), MovieDTO.fromDomain(optionalMovie01.get()), MovieDTO.fromDomain(optionalMovie02.get()));
    }
}

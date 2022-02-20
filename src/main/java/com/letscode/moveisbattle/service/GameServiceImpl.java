package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private MovieService movieService;
    private QuestionService questionService;

    @Override
    public Question startGame(String userId) {
        Game game = new Game(userId);
        game = saveGame(game);

        Question question = questionService.generateQuestion(game);
        game.setLastQuestion(question.getQuestionId());

        saveGame(game);

        return question;
    }

    @Override
    public Question stopGame(String userId, String gameId) {
        return null;
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Optional<Game> getGame(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public ResponseEntity<ResultResponse> guess(GuessResquest guessResquest) {
        Optional<Game> gameOptional = getGame(guessResquest.getGameId());
        ResultResponse result = new ResultResponse();
        result.setGameId(guessResquest.getGameId());

        if (gameOptional.isEmpty()) {
            result.setGameStatus("Game not found");
            return ResponseEntity.status(404).body(result);
        }

        Game game = gameOptional.get();

        Movie movie01 = movieService.getMovie(guessResquest.getMovie01());
        Movie movie02 = movieService.getMovie(guessResquest.getMovie02());
        String questionHash = game.getId() + movie01.getId() + movie02.getId();


        if (!questionHash.equals(game.getLastQuestionId())) {
            result.setGameStatus("Invalid question for selected game");
            return ResponseEntity.status(400).body(result);
        }

        if (game.getWrongAnswers() < 3) {
            String bestMovieId;

            if (movie01.getRating() > movie02.getRating()) {
                bestMovieId = movie01.getId();
            } else if (movie01.getRating() < movie02.getRating()) {
                bestMovieId = movie02.getId();
            } else {
                bestMovieId = "same";
            }

            if (bestMovieId.equals(guessResquest.getGuess()) || bestMovieId.equals("same")) {
                result.setLastGuessResult("You was right!");
                game.setRightAnswers(game.getRightAnswers() + 1);
            } else {
                result.setLastGuessResult("Ooops! You was wrong!");
                game.setWrongAnswers(game.getWrongAnswers() + 1);
            }
        }

        result.setPoints(game.getRightAnswers());

        if (game.getWrongAnswers() < 3) {
            Question newQuestion = questionService.generateQuestion(game);
            game.setLastQuestion(newQuestion.getQuestionId());
            saveGame(game);
            result.setLastQuestion(newQuestion);
            return ResponseEntity.ok(result);
        }

        saveGame(game);

        result.setGameStatus("Game Over!");

        return ResponseEntity.ok(result);
    }
}

package com.letscode.moveisbattle.service.impl;

import com.letscode.moveisbattle.model.*;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.repository.GameRepository;
import com.letscode.moveisbattle.service.GameService;
import com.letscode.moveisbattle.service.MovieService;
import com.letscode.moveisbattle.service.QuestionService;
import com.letscode.moveisbattle.service.UserStatusService;
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
    private UserStatusService userStatusService;

    @Override
    public ResponseEntity<Question> startGame(Long userId) {
        Game game = new Game(userId);
        game = saveGame(game);

        Question question = questionService.generateQuestion(game);
        game.setLastQuestion(question.getQuestionId());

        saveGame(game);

        return ResponseEntity.ok(question);
    }

    @Override
    public ResponseEntity<Game> stopGame(Long userId, String gameId) {
        Optional<Game> gameOptional = getGame(gameId);
        Optional<UserStatus> userStatusOptional = userStatusService.getUserStatus(userId);

        if (gameOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (userStatusOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Game game = gameOptional.get();
        UserStatus userStatus = userStatusOptional.get();

        if (!game.isValidGame()) {
            return ResponseEntity.ok(game);
        }

        game.setValidGame(false);
        userStatus.setGameAnswersStatus(game.getRightAnswers(), game.getWrongAnswers());

        saveGame(game);
        userStatusService.saveUserStatus(userStatus);

        return ResponseEntity.ok(game);
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

        Movie movie01 = movieService.getMovie(guessResquest.getMovie01());
        Movie movie02 = movieService.getMovie(guessResquest.getMovie02());

        Game game = gameOptional.get();
        String questionHash = game.getId() + movie01.getId() + movie02.getId();


        if (!questionHash.equals(game.getLastQuestionId())) {
            result.setGameStatus("Invalid question for selected game");
            return ResponseEntity.status(400).body(result);
        }

        if (game.isValidGame()) {
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

                if (game.getWrongAnswers() == 3) {
                    game.setValidGame(false);
                }
            }
        }

        result.setPoints(game.getRightAnswers());

        if (game.isValidGame()) {
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

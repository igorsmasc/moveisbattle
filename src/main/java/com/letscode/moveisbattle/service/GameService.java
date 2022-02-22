package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface GameService {
    ResponseEntity<Question> startGame(Long userId);

    ResponseEntity<Game> stopGame(Long userId, String gameId);

    Game saveGame(Game game);

    Optional<Game> getGame(String id);

    ResponseEntity<ResultResponse> guess(GuessResquest guessResquest);
}

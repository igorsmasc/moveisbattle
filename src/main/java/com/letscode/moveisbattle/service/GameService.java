package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface GameService {
    Question startGame(String userId);

    Question stopGame(String userId, String gameId);

    Game saveGame(Game game);

    Optional<Game> getGame(String id) throws ChangeSetPersister.NotFoundException;

    ResponseEntity<ResultResponse> guess(GuessResquest guessResquest);
}

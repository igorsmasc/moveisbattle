package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class GameController {

    private final GameService gameService;

    @PostMapping("/start{userId}")
    public Question startGame(@PathVariable String userId) {

        return gameService.startGame(userId);
    }

    @PostMapping("/guess")
    public ResponseEntity<?> guess(@RequestBody GuessResquest guessResquest) {
        return gameService.guess(guessResquest);
    }

}

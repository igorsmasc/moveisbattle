package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class GameController {

    private final GameService gameService;

    @PostMapping("/start")
    public Question startGame() {
        return gameService.startGame("1");
    }

    @PostMapping("/guess")
    public ResponseEntity<?> guess(@RequestBody GuessResquest guessResquest) {
        return gameService.guess(guessResquest);
    }
}

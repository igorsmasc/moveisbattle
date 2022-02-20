package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "games")
public class GameController {
    private final GameService gameService;

    @PostMapping("/start")
    public Question startGame() {
        return gameService.startGame("1");
    }

    @PostMapping("/stop")
    public ResponseEntity<Game> stopGame() {
        return gameService.stopGame("1", "1");
    }

    @PostMapping("/guess")
    public ResponseEntity<ResultResponse> guess(@RequestBody GuessResquest guessResquest) {
        return gameService.guess(guessResquest);
    }
}

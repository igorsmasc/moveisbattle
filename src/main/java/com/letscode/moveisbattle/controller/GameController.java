package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
@Api(value = "Games")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;

    @PostMapping("/start/{userId}/game")
    @ApiOperation(value = "Iniciar um novo jogo")
    public ResponseEntity<Question> startGame(Long userId) {
        return gameService.startGame(userId);
    }

    @PostMapping("/stop/{userId}/game/{gameId}")
    @ApiOperation(value = "Encerrar um jogo")
    public ResponseEntity<Game> stopGame(@RequestParam Long userId, @RequestParam String gameId) {
        return gameService.stopGame(userId, gameId);
    }

    @PostMapping("/guess")
    @ApiOperation(value = "Dar um palpite em um derterminado jogo")
    public ResponseEntity<ResultResponse> guess(@RequestBody GuessResquest guessResquest) {
        return gameService.guess(guessResquest);
    }
}

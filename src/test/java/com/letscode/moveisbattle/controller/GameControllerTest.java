package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    private GameController underTest;

    @BeforeEach
    void setup() {
        underTest = new GameController(gameService);
    }

    @Test
    void shouldStartGame() {
        // Given
        Long appUserId = 1L;

        // When
        underTest.startGame(appUserId);

        // Then
        verify(gameService, times(1)).startGame(appUserId);
    }

    @Test
    void shouldStopGame() {
        // Given
        Long appUserId = 1L;
        String gameId = "1";

        // When
        underTest.stopGame(appUserId, gameId);

        // Then
        verify(gameService, times(1)).stopGame(appUserId, gameId);
    }

    @Test
    void shouldGuess() {
        // Given
        GuessResquest guessResquest = new GuessResquest("game-id", "movie-id-01", "movie-id-02", "movie-id-01");

        // When
        underTest.guess(guessResquest);

        // Then
        verify(gameService, times(1)).guess(guessResquest);
    }

}

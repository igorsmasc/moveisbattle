package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.User;
import com.letscode.moveisbattle.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserService userService;


    private GameService underTest;

    @BeforeEach
    void setup() {
        underTest = new GameServiceImpl(gameRepository, movieService, questionService, userService);
    }

    @Test
    void canStartGame() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        underTest.startGame(user.getId());

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(2)).save(gameArgumentCaptor.capture());
    }

    @Test
    void canStopGame() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(userService.getUser(user.getId())).willReturn(Optional.of(user));

        // When
        underTest.stopGame(user.getId(), game.getId());

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());
        verify(userService, times(1)).saveUser(userArgumentCaptor.capture());
    }

    @Test
    void canSaveGame() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        // When
        underTest.saveGame(game);

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());
    }

    @Test
    void canGetGame() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        // When
        underTest.getGame(game.getId());

        // Then
        verify(gameRepository, times(1)).findById(game.getId());
    }

    @Test
    void canGuess() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        // When
        underTest.getGame(game.getId());

        // Then
        verify(gameRepository, times(1)).findById(game.getId());
    }

}

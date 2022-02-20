package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.User;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void shouldStartGame() {
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
    void shouldStopGameSuccessfully() {
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
    void shouldReturnNotFoundWhenStopAndGameDoesntExists() {
        // Given
        String invalidGameId = "test-invalid-game-id";

        User user = new User("Test User");
        user.setId("test-user-id");

        given(underTest.getGame(invalidGameId)).willReturn(Optional.empty());
        given(userService.getUser(user.getId())).willReturn(Optional.of(user));

        // When
        ResponseEntity<Game> responseEntity = underTest.stopGame(user.getId(), invalidGameId);

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userService, times(0)).saveUser(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenGuessAndGameDoesntExists() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-invalid-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());


        given(underTest.getGame(game.getId())).willReturn(Optional.empty());
 
        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userService, times(0)).saveUser(any());
        Assertions.assertEquals("Game not found", responseEntity.getBody().getGameStatus());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenGameQuestionHashIsInvalid() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("invalid-last-question-id");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Assertions.assertEquals("Invalid question for selected game", responseEntity.getBody().getGameStatus());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnWithoutActionsWhenGameIsInvalid() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setValidGame(false);

        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(userService.getUser(user.getId())).willReturn(Optional.of(user));

        // When
        ResponseEntity<Game> responseEntity = underTest.stopGame(user.getId(), game.getId());

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userService, times(0)).saveUser(any());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void shouldSaveGamSuccessfully() {
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
    void shouldGetGameSuccessfully() {
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
    void shouldGuessFirstMovieAndAnswerRightSuccessfully() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.7, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 1;
        Integer wrongAnswers = 0;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("You was right!", responseEntity.getBody().getLastGuessResult());

    }

    @Test
    void shouldGuessSecondMovieAndAnswerRightSuccessfully() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m2.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 1;
        Integer wrongAnswers = 0;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("You was right!", responseEntity.getBody().getLastGuessResult());

    }

    @Test
    void shouldGuessAndAnswerRightSuccessfullyWhenMoviesHasSameRate() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.0, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m2.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 1;
        Integer wrongAnswers = 0;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("You was right!", responseEntity.getBody().getLastGuessResult());

    }

    @Test
    void shouldGuessAndAnswerWrongSuccessfully() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 0;
        Integer wrongAnswers = 1;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("Ooops! You was wrong!", responseEntity.getBody().getLastGuessResult());
    }

    @Test
    void shouldReturnGameOverWhenThreeWrongQuestions() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(user.getId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);
        game.setWrongAnswers(2);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(underTest.saveGame(any())).willReturn(game);

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 0;
        Integer wrongAnswers = 3;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("Ooops! You was wrong!", responseEntity.getBody().getLastGuessResult());
        Assertions.assertEquals("Game Over!", responseEntity.getBody().getGameStatus());
    }

}

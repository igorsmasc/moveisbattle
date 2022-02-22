package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.model.response.ResultResponse;
import com.letscode.moveisbattle.repository.GameRepository;
import com.letscode.moveisbattle.service.impl.GameServiceImpl;
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
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserStatusService userStatusService;


    private GameService underTest;

    @BeforeEach
    void setup() {
        underTest = new GameServiceImpl(gameRepository, movieService, questionService, userStatusService);
    }

    @Test
    void shouldStartGame() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");

        given(questionService.generateQuestion(any())).willReturn(question);
        given(underTest.saveGame(any())).willReturn(game);

        // When
        underTest.startGame(userStatus.getUserId());

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(2)).save(gameArgumentCaptor.capture());
        Assertions.assertEquals(game, gameArgumentCaptor.getValue());
    }

    @Test
    void shouldStopGameSuccessfully() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");

        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(userStatusService.getUserStatus(userStatus.getUserId())).willReturn(Optional.of(userStatus));

        // When
        underTest.stopGame(userStatus.getUserId(), game.getId());

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        ArgumentCaptor<UserStatus> userStatusArgumentCaptor = ArgumentCaptor.forClass(UserStatus.class);
        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());
        verify(userStatusService, times(1)).saveUserStatus(userStatusArgumentCaptor.capture());
    }

    @Test
    void shouldReturnNotFoundWhenStopAndGameDoesntExists() {
        // Given
        String invalidGameId = "test-invalid-game-id";

        UserStatus userStatus = new UserStatus(1L);

        given(underTest.getGame(invalidGameId)).willReturn(Optional.empty());
        given(userStatusService.getUserStatus(userStatus.getUserId())).willReturn(Optional.of(userStatus));

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.stopGame(userStatus.getUserId(), invalidGameId);

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userStatusService, times(0)).saveUserStatus(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenStopAndUserStatusDoesntExists() {
        // Given
        Long invalidUserStatusId = 1L;

        Game game = new Game(invalidUserStatusId);
        game.setId("test-invalid-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(userStatusService.getUserStatus(invalidUserStatusId)).willReturn(Optional.empty());

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.stopGame(invalidUserStatusId, game.getId());

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userStatusService, times(0)).saveUserStatus(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenGuessAndGameDoesntExists() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-invalid-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), m1.getId(), m2.getId(), m1.getId());


        given(underTest.getGame(game.getId())).willReturn(Optional.empty());

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userStatusService, times(0)).saveUserStatus(any());
        Assertions.assertEquals("Game not found", responseEntity.getBody().getGameStatus());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenGameQuestionHashIsInvalid() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("invalid-last-question-id");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie01.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Assertions.assertEquals("Invalid question for selected game", responseEntity.getBody().getGameStatus());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnWithoutActionsWhenGameIsInvalid() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setValidGame(false);

        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(userStatusService.getUserStatus(userStatus.getUserId())).willReturn(Optional.of(userStatus));

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.stopGame(userStatus.getUserId(), game.getId());

        // Then
        verify(gameRepository, times(0)).save(any());
        verify(userStatusService, times(0)).saveUserStatus(any());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldSaveGamSuccessfully() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");

        // When
        underTest.saveGame(game);

        // Then
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());
        Assertions.assertEquals(game, gameArgumentCaptor.getValue());
    }

    @Test
    void shouldGetGameSuccessfully() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");

        // When
        underTest.getGame(game.getId());

        // Then
        verify(gameRepository, times(1)).findById(game.getId());
    }

    @Test
    void shouldGuessFirstMovieAndAnswerRightSuccessfully() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.7);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie01.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
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
    void whenDoesntExistsShouldReturn404() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.7);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie01.getId());

        given(underTest.getGame(game.getId())).willReturn(Optional.empty());

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("Game not found", responseEntity.getBody().getGameStatus());
    }

    @Test
    void shouldGuessSecondMovieAndAnswerRightSuccessfully() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie02.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
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
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.0);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie02.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
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
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie01.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
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
        UserStatus userStatus = new UserStatus(1L);

        Question question = new Question();
        question.setQuestionId("1");

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");
        game.setLastQuestionId("test-game-id0102");
        game.setValidGame(true);
        game.setWrongAnswers(2);

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        GuessResquest guessResquest = new GuessResquest(game.getId(), movie01.getId(), movie02.getId(), movie01.getId());

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);
        given(underTest.getGame(game.getId())).willReturn(Optional.of(game));
        given(underTest.saveGame(any())).willReturn(game);
        given(userStatusService.getUserStatus(game.getUserId())).willReturn(Optional.of(userStatus));

        // When
        ResponseEntity<ResultResponse> responseEntity = underTest.guess(guessResquest);

        // Then
        Integer rightAnswers = 0;
        Integer wrongAnswers = 3;

        Assertions.assertEquals(rightAnswers, game.getRightAnswers());
        Assertions.assertEquals(wrongAnswers, game.getWrongAnswers());
        Assertions.assertEquals("Game Over!", responseEntity.getBody().getGameStatus());
    }
}

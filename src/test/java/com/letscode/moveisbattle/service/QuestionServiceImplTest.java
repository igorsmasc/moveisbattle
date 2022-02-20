package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.dto.MovieDTO;
import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private MovieService movieService;

    private QuestionService underTest;

    @BeforeEach
    void setup() {
        underTest = new QuestionServiceImpl(movieService);
    }

    @Test
    void carGenerateQuestion() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        Game game = new Game(user.getId());
        game.setId("test-game-id");

        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");
        Movie m2 = new Movie("02", "id-imdb-02", "movie 02", 2020, 9.2, "genre", "type");

        given(movieService.getMovie(any())).willReturn(Optional.of(m1)).willReturn(Optional.of(m2));

        // When
        Question question = underTest.generateQuestion(game);

        // Then
        Assertions.assertEquals(question.getGameId(), game.getId());
        Assertions.assertEquals(question.getMovie01(), MovieDTO.fromDomain(m1));
        Assertions.assertEquals(question.getMovie02(), MovieDTO.fromDomain(m2));
    }
}
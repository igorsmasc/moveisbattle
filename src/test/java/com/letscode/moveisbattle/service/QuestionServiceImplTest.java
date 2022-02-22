package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.dto.MovieDTO;
import com.letscode.moveisbattle.model.*;
import com.letscode.moveisbattle.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

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
        UserStatus userStatus = new UserStatus(1L);

        Game game = new Game(userStatus.getUserId());
        game.setId("test-game-id");

        Movie movie01 = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        Movie movie02 = new Movie("02", "id-imdb-02", "movie 02", 9.2);

        given(movieService.getMovie(any())).willReturn(movie01).willReturn(movie02);

        // When
        Question question = underTest.generateQuestion(game);

        // Then
        Assertions.assertEquals(question.getGameId(), game.getId());
        Assertions.assertEquals(question.getMovie01(), MovieDTO.fromDomain(movie01));
        Assertions.assertEquals(question.getMovie02(), MovieDTO.fromDomain(movie02));
    }
}

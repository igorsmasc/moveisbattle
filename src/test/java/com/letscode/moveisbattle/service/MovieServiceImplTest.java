package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.repository.MovieRepository;
import com.letscode.moveisbattle.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService underTest;

    @BeforeEach
    void setup() {
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    void shouldGetMovie() {
        // Given
        Movie movie = new Movie("01", "id-imdb-01", "movie 01", 9.0);
        given(movieRepository.findById(movie.getId())).willReturn(Optional.of(movie));

        // When
        underTest.getMovie(movie.getId());

        // Then
        verify(movieRepository, times(1)).findById(any());
    }

    @Test
    void whenMovieIdDoesntExistsShouldReturnIllegalStateException() {
        // Given
        String invalidMovieId = "invalid-movie-id";
        given(movieRepository.findById(invalidMovieId)).willReturn(Optional.empty());

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.getMovie(invalidMovieId));
    }
}

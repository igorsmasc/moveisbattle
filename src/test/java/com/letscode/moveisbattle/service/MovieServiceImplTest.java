package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.repository.MovieRepository;
import com.letscode.moveisbattle.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService underTest;

    @BeforeEach
    void setup() {
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    void canGetMovie() {
        // Given
        Movie m1 = new Movie("01", "id-imdb-01", "movie 01", 2020, 9.0, "genre", "type");

        // When
        underTest.getMovie(m1.getId());

        // Then
        verify(movieRepository, times(1)).findById(any());
    }
}

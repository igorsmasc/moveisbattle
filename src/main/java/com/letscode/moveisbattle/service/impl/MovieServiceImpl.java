package com.letscode.moveisbattle.service.impl;

import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.repository.MovieRepository;
import com.letscode.moveisbattle.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie getMovie(String id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isEmpty()) {
            throw new IllegalStateException("Movie id " + id + " is invalid");
        }

        return movieOptional.get();
    }
}

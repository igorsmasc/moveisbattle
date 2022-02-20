package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Movie;

import java.util.Optional;

public interface MovieService {
    Optional<Movie> getMovie(String id);
}

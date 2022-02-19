package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.gateway.MoviesGateway;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie getMovie(String id) {;
       return movieRepository.getById(id);
    }
}

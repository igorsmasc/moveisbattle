package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.gateway.MoviesGateway;
import com.letscode.moveisbattle.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MoviesGateway moviesGateway;

    @Override
    public Movie getMovie(String id) {;
        if(id.equals("1")) {
            return new Movie("1", "title", 2020, 9.0, "zzz", "zzz");
        }

        return new Movie("2", "title 2", 2020, 8.0, "zzz 2", "zz 2");

    }
}

package com.letscode.moveisbattle.gateway;

import com.letscode.moveisbattle.model.Movie;
import reactor.core.publisher.Mono;

public interface MoviesGateway {
    Mono<Movie> getMovieData(String id);
}

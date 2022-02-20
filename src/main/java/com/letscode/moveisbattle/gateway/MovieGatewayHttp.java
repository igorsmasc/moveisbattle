package com.letscode.moveisbattle.gateway;

import com.letscode.moveisbattle.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
@Service
@RequiredArgsConstructor
public class MovieGatewayHttp implements MoviesGateway {
    @Override
    public Mono<Movie> getMovieData(String id) {
        return null;
    }
}

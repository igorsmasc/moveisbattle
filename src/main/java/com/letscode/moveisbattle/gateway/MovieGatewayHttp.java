package com.letscode.moveisbattle.gateway;

import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@Service
@RequiredArgsConstructor
public class MovieGatewayHttp implements MoviesGateway {
    @Override
    public Mono<Movie> getMovieData(String id) {
        return null;
    }

//    public final WebClient webClient;
//
//    @Override
//    public Mono<Movie> getMovieData(String id) {
//        return webClient.get().uri("").retrieve().bodyToMono(MovieRequest.class).map(MovieRequest::toDomain);
//    }
}

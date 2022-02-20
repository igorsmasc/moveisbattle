package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/{movieId}")
    public String getMovie() {
        return "orderId";
    }
}

package com.letscode.moveisbattle.dto;

import com.letscode.moveisbattle.model.Movie;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class MovieDTO {
    private String id;
    private String imdbId;
    private String title;

    public static MovieDTO fromDomain(Movie movie) {
        return MovieDTO.builder().id(movie.getId()).imdbId(movie.getImdbId()).title(movie.getTitle()).build();
    }
}

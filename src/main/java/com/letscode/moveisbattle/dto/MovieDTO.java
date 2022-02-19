package com.letscode.moveisbattle.dto;

import com.letscode.moveisbattle.model.Movie;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Builder
public class MovieDTO {
    private String id;
    private String title;

    public MovieDTO(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public static MovieDTO fromDomain(Movie movie) {
        return MovieDTO.builder().id(movie.getId()).title(movie.getTitle()).build();
    }
}

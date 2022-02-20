package com.letscode.moveisbattle.model.request;

import com.letscode.moveisbattle.model.Movie;
import lombok.*;

@Getter
public class MovieRequest {
    private String imdbID;
    private String title;
    private Integer year;
    private Double imdbRating;
    private String genre;
    private String type;

    public Movie toDomain() {
        return Movie.builder().id(imdbID).title(title).year(year).rating(imdbRating).genre(genre).type(type).build();
    }
}

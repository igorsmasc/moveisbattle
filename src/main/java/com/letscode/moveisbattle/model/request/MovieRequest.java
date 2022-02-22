package com.letscode.moveisbattle.model.request;

import lombok.*;

@Getter
public class MovieRequest {
    private String imdbID;
    private String title;
    private Integer year;
    private Double imdbRating;
    private String genre;
    private String type;
}

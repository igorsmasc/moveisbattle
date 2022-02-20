package com.letscode.moveisbattle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String imdbId;
    private String title;
    private Integer year;
    private Double rating;
    private String genre;
    private String type;

    public Movie(String id, String imdbId, String title, Integer year, Double rating, String genre, String type) {
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
        this.type = type;
    }
}

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
    private Double rating;

    public Movie(String id, String imdbId, String title, Double rating) {
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.rating = rating;
    }
}
